package com.bigdata.akka.cluster.actor.pubsub

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish

class PublisherParamActor(topicName:String) extends Actor with ActorLogging {
	val mediator = DistributedPubSub(context.system).mediator
	
	def receive = {
		case msg:String 	=> 
			log.debug("PublisherActor receive msg : {}, self : {}", msg, self)
			mediator ! Publish(topicName, s"send msg to subscriber : $msg")
    	case _ @ etc 			=> log.debug("etc msg : {}, self : {}", etc, self)
  	}
}