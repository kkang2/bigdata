package com.bigdata.akka.scala.http

import scala.io.StdIn
import akka.stream.ActorMaterializer
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.server.Directives._

object SimpleMain extends App {
	implicit val system = ActorSystem("httpActorSystem")
   implicit val materializer = ActorMaterializer()
   // needed for the future flatMap/onComplete in the end
   implicit val executionContext = system.dispatcher

   val route = path("hello") {
   	get {
      	complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Get Request Say hello to akka-http</h1>"))
      }
	}

    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
}