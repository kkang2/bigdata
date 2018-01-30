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
   
   final case class Data(contents:String)

   val route = 
   	path("hello") {
	   	get {
	   		Thread.sleep(2000)
	   		println("쉬자쉬자")
	   		Thread.sleep(2000)
	   		println("쉬자쉬자")
	   		Thread.sleep(2000)
	   		println("쉬자쉬자")
	   		Thread.sleep(2000)
	   		println("쉬자쉬자")
	   		Thread.sleep(2000)
	   		println("쉬자쉬자")
	   		Thread.sleep(2000)
	   		println("쉬자쉬자")
	      	complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Get Request Say hello to akka-http</h1>"))
	      }
		} ~
		path("hi") {
	   	get {
	      	complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Get Request Say hi to akka-http</h1>"))
	      }
		} ~
		path("tt") {
	   	post {
	   		entity(as[String]) {	data =>
	   			println("post 도착 : " + data)
	   			complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Get Request Say hi to akka-http</h1>"))
	   		}
	      }
		} /*~
		path("/") {
	   	post {
	   		println("post 도착")
	      	complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Get Request Say hi to akka-http</h1>"))
	      }
		}*/

    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)

    println(s"Server online at http://0.0.0.0:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
}