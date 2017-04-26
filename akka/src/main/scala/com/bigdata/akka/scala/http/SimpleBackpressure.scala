package com.bigdata.akka.scala.http

import scala.io.StdIn
import akka.stream.ActorMaterializer
import akka.actor.ActorSystem
import scala.util.Random
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpEntity
import akka.stream.scaladsl.Source
import akka.http.scaladsl.model.ContentTypes
import akka.util.ByteString
import akka.http.scaladsl.server.Directives._

/*
 * curl --limit-rate 50b 127.0.0.1:8080/random    <= Backpressure 테스트
 **/
object SimpleBackpressure extends App {
	implicit val system = ActorSystem()
   implicit val materializer = ActorMaterializer()
   // needed for the future flatMap/onComplete in the end
   implicit val executionContext = system.dispatcher

   // streams are re-usable so we can define it here
   // and use it for every request
   val numbers = Source.fromIterator(() =>Iterator.continually(Random.nextInt()))

   val route =
     path("random") {
       get {
         complete(
           HttpEntity(
             ContentTypes.`text/plain(UTF-8)`,
             // transform each number to a chunk of bytes
             numbers.map(n => ByteString(s"$n\n"))
           )
         )
       }
     }

    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 10080)
    
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
}