package com.bigdata.akka.cluster.actor.pubsub

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish

class PublisherGroupActor extends Actor with ActorLogging {
	val mediator = DistributedPubSub(context.system).mediator
	
	def receive = {
		case msg:String 	=> 
			log.debug("PublisherGroupActor receive msg : {}, self : {}", msg, self)
			mediator ! Publish("content", s"send msg to subscriber : $msg", true)
    	case _ @ etc 			=> log.debug("etc msg : {}, self : {}", etc, self)
  	}
}