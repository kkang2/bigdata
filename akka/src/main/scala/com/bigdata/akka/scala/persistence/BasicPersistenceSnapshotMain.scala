package com.bigdata.akka.scala.persistence

import com.bigdata.akka.scala.persistence.actor.BasicPersistenceSnapshotActor

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala

object BasicPersistenceSnapshotMain extends App {
	val system 					= ActorSystem("BasicPersistenceSnapshotSystem") // create ActorSystem
  	val persistenceActor	= system.actorOf(Props[BasicPersistenceSnapshotActor], "basicPersistenceSnapshotActor") // create Actor
  	
  	persistenceActor ! "snapshot"
}