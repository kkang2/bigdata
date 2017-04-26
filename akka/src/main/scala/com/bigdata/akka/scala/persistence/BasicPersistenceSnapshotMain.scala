package com.bigdata.akka.scala.persistence

import akka.actor.ActorSystem
import akka.actor.Props
import com.bigdata.akka.basic.Greeter
import com.bigdata.akka.scala.persistence.actor.BasicPersistenceSnapshotActor

object BasicPersistenceSnapshotMain extends App {
	val system 					= ActorSystem("BasicPersistenceSnapshotSystem") // create ActorSystem
  	val persistenceActor	= system.actorOf(Props[BasicPersistenceSnapshotActor], "basicPersistenceSnapshotActor") // create Actor
  	
  	persistenceActor ! "snapshot"
}