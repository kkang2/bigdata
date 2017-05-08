package com.bigdata.akka.scala.persistence

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import com.bigdata.akka.scala.persistence.actor.BasicPersistenceActor

object BasicPersistenceMain extends App {
	val system 					= ActorSystem("BasicPersistenceSystem") // create ActorSystem
  	val persistenceActor	= system.actorOf(Props[BasicPersistenceActor], "basicPersistenceActor") // create Actor
  	
  	persistenceActor ! "persist"
}