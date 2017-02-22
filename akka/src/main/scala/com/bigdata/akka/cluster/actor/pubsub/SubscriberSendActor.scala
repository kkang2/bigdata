package com.bigdata.akka.cluster.actor.pubsub

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe
import akka.cluster.pubsub.DistributedPubSubMediator.Put

class SubscriberSendActor extends Actor with ActorLogging {
	val mediator = DistributedPubSub(context.system).mediator
  	
	// register to the path
  	mediator ! Put(self)

	def receive = {
		case msg:String 	=> log.debug("SubscriberSendActor receive msg : {}, self : {}", msg, self)
    	case _ @ etc 			=> log.debug("etc msg : {}, self : {}", etc, self)
  	}
}