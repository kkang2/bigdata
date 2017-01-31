package com.bigdata.akka.persistence

import akka.actor.ActorSystem
import akka.actor.Props
import com.bigdata.akka.basic.Greeter
import com.bigdata.akka.persistence.actor.BasicPersistenceActor

object BasicPersistenceMain extends App {
	val system 					= ActorSystem("BasicPersistenceSystem") // create ActorSystem
  	val persistenceActor	= system.actorOf(Props[BasicPersistenceActor], "basicPersistenceActor") // create Actor
  	
  	persistenceActor ! "persist"
}