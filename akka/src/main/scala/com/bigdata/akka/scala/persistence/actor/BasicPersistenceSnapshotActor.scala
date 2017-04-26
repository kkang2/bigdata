package com.bigdata.akka.scala.persistence.actor

import akka.persistence.PersistentActor
import akka.persistence.SnapshotOffer
import akka.persistence.SaveSnapshotFailure
import akka.persistence.SaveSnapshotSuccess
import akka.actor.ActorLogging
import java.util.Date

case class SnapshotHistory(date:Date)

class BasicPersistenceSnapshotActor extends PersistentActor with ActorLogging {
	override def persistenceId: String = "snapshot1"    // Id of the persistent entity

	/* PersistentActor 시작, 재시작 시 자동호출 */
  	override def receiveRecover: Receive = {
   	case history:SnapshotHistory													=> log.debug("history : {}", history)
   	case SnapshotOffer(metadata, data: SnapshotHistory) 	=> log.debug("metadata : {}, data : {}", metadata, data)
   	case etc @ _ 																					=> log.debug("etc : {}", etc)
  	}

  	override def receiveCommand: Receive = {
  		case "snapshot" =>
  			val history = SnapshotHistory(new Date())
  			saveSnapshot(history)  // History case class saveSnapshot
  		case SaveSnapshotSuccess(metadata)         		=> log.debug("SaveSnapshotSuccess => metadata : {}", metadata) 
      case SaveSnapshotFailure(metadata, cause) 		=> log.debug("SaveSnapshotFailure => metadata : {}, cause : {}", metadata, cause) 
	}
}