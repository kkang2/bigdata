package com.bigdata.akka.persistence.actor

import akka.persistence.PersistentActor
import akka.persistence.SnapshotOffer
import akka.persistence.SaveSnapshotFailure
import akka.persistence.SaveSnapshotSuccess
import akka.actor.ActorLogging
import java.util.Date

case class History(date:Date)

class BasicPersistenceActor extends PersistentActor with ActorLogging {
	override def persistenceId: String = "sample1"    // Id of the persistent entity

	/* PersistentActor 시작, 재시작 시 자동호출 */
  	override def receiveRecover: Receive = {
   	case history:History => log.debug("history : {}", history)
   	case etc @ _ 				=> log.debug("etc : {}", etc)
  	}

  	override def receiveCommand: Receive = {
  		case "persist" =>
  			val history = History(new Date())
  			persist(history)(event => println("persist => " + event))  // History case class save
	}
}