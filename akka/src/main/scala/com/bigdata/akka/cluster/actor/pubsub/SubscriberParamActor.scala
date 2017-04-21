package com.bigdata.akka.cluster.actor.pubsub

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe
import akka.cluster.pubsub.DistributedPubSubMediator.SubscribeAck

class SubscriberParamActor(topicName:String) extends Actor with ActorLogging {
	val mediator = DistributedPubSub(context.system).mediator
  	// subscribe to the topic named "content"
  	mediator ! Subscribe(topicName, self)

	def receive = {
		case msg:String 	=> log.debug("receive msg : {}, self : {}", msg, self)
		case SubscribeAck(Subscribe("content", None, self)) => log.debug("SubscribeAck, self : {}", self);
    	case _ @ etc 			=> log.debug("etc msg : {}, self : {}", etc, self)
  	}
}