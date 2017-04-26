package com.bigdata.akka.scala.cluster.actor.client

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish

class ReceptionActor extends Actor with ActorLogging {
	val mediator = DistributedPubSub(context.system).mediator
	
	def receive = {
		case msg:String 	=> log.debug("ReceptionActor receive msg : {}, self : {}", msg, self)
    	case _ @ etc 			=> log.debug("etc msg : {}, self : {}", etc, self)
  	}
}