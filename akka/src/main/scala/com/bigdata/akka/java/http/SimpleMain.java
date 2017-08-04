package com.bigdata.akka.java.http;

import java.util.concurrent.CompletionStage;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

public class SimpleMain extends AllDirectives {

	public static void main(String[] args) throws Exception {
		// boot up server using the route as defined below
	    ActorSystem system = ActorSystem.create("httpSampleSystem");

	    final Http http = Http.get(system);
	    final ActorMaterializer materializer = ActorMaterializer.create(system);

	    //In order to access all directives we need an instance where the routes are define.
	    SimpleMain app = new SimpleMain();

	    final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow(system, materializer);
	    final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
	        ConnectHttp.toHost("0.0.0.0", 8080), materializer);

	    System.out.println("Server online at http://0.0.0.0:8080/\nPress RETURN to stop...");
	    System.in.read(); // let it run until user presses return

	    binding
	        .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
	        .thenAccept(unbound -> system.terminate()); // and shutdown when done
	}
	
	private Route createRoute() {
	    return route(
	        path("hello", () ->
	            get(() ->
	                complete("<h1>Say hello to akka-http</h1>"))));
	  }
}
