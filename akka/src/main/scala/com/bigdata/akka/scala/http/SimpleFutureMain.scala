package com.bigdata.akka.scala.http

import scala.io.StdIn
import akka.stream.ActorMaterializer
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import scala.concurrent.Future
import akka.Done
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import scala.util.Random

object SimpleFutureMain extends App {
	// domain model
  	final case class Item(name: String, id: Long)
  	final case class Order(items: List[Item])

  	// formats for unmarshalling and marshalling
  	implicit val itemFormat = jsonFormat2(Item)
  	implicit val orderFormat = jsonFormat1(Order)

  	// (fake) async database query api
  	def fetchItem(itemId: Long): Future[Option[Item]] = Future {
  	  val trueOrFalse = Random.nextBoolean()
  	  
  	  println("trueOrFalse : " + trueOrFalse)
  	  
  	  if(trueOrFalse) Some(Item("New Item", itemId)) else	 None
	}
  
  	/*
  	 * akka.Done (for signaling successful completion) and akka.NotUsed (for materialization results of stages that do not produce a value)
  	 **/
  	def saveOrder(order: Order): Future[Done] = Future {
  		akka.Done
  	}
  
  	// needed to run the route
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    // needed for the future map/flatmap in the end
    implicit val executionContext = system.dispatcher

    val route: Route =
      get {
        pathPrefix("item" / LongNumber) { id =>
          // there might be no item for a given id
          val maybeItem: Future[Option[Item]] = fetchItem(id)

          onSuccess(maybeItem) {
            case Some(item) 	=> complete(item)
            case None       		=> complete(StatusCodes.NotFound)
          }
        }
      } ~
     post {
       path("create-order") {
         entity(as[Order]) { order =>
           val saved: Future[Done] = saveOrder(order)
           onComplete(saved) { done =>
             complete("order created")
           }
         }
       }
     }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ ⇒ system.terminate()) // and shutdown when done
}