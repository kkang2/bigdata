package com.bigdata.akka.cluster.pubsub

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.actor.Props
import com.bigdata.akka.cluster.actor.pubsub.SubscriberActor
import com.bigdata.akka.cluster.actor.pubsub.PublisherActor

object PublishSimpleMain extends App {
	val system = ActorSystem("ClusterSystem", ConfigFactory.parseResources("cluster.conf")) // create ActorSystem
  	
	val subActor1	= system.actorOf(Props[SubscriberActor], "subActor1") // create Actor
  	val subActor2	= system.actorOf(Props[SubscriberActor], "subActor2") // create Actor
  	
  	val pubActor = system.actorOf(Props[PublisherActor], "pubActor") // create Actor
  	
  	pubActor ! "Hi!!!"
}