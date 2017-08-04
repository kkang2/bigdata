package com.bigdata.akka.scala.stream.source

import akka.stream.scaladsl.Source
import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object BasicSource extends App {
	implicit val system = ActorSystem("QuickStart")
	implicit val materializer = ActorMaterializer()

	val intSource: Source[Int, NotUsed] = Source(1 to 100)
	
	intSource.runForeach { i => println(i) }
	intSource.runForeach { i => println(i) }
}