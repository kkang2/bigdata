package com.bigdata.akka.cluster.actor.pubsub

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish
import akka.cluster.pubsub.DistributedPubSubMediator.Send

class PublisherSendActor extends Actor with ActorLogging {
	val mediator = DistributedPubSub(context.system).mediator
	
	def receive = {
		case msg:String 	=>
			log.debug("PublisherSendActor receive msg : {}, self : {}", msg, self)
			mediator ! Send(path = "/user/sendActor", msg = s"send msg to subscriber : $msg", localAffinity = false)
    	case _ @ etc 			=> log.debug("etc msg : {}, self : {}", etc, self)
  	}
}