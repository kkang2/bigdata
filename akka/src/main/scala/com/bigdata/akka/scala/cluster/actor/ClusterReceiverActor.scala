package com.bigdata.akka.scala.cluster.actor

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.cluster.Cluster

class ClusterReceiverActor extends Actor with ActorLogging {
	val cluster = Cluster(context.system)
 
  	def receive = {
		case msg:String 	=> log.debug("receive msg : {}, self : {}", msg, self)
    	case _ @ etc 			=> log.debug("etc msg : {}, self : {}", etc, self)
  	}
	
  	override def preStart(): Unit = {
  		log.debug("ClusterReceiverActor preStart!!! self : {}", self)
  	}
}