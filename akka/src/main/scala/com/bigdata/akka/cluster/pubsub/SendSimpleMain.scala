package com.bigdata.akka.cluster.pubsub

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.actor.Props
import com.bigdata.akka.cluster.actor.pubsub.PublisherSendActor
import com.bigdata.akka.cluster.actor.pubsub.SubscriberSendActor

object SendSimpleMain extends App {
	val system = ActorSystem("ClusterSystem", ConfigFactory.parseResources("cluster2.conf")) // create ActorSystem
  	
	val sendActor	= system.actorOf(Props[SubscriberSendActor], "sendActor") // create Actor
  	//val subActor2	= system.actorOf(Props[SubscriberSendActor], "sendActor") // create Actor
  	
  	/*val pubActor = system.actorOf(Props[PublisherSendActor], "pubSendActor") // create Actor
  	
  	
  	Thread.sleep(20000)
  	
  	pubActor ! "Hi!!!"
  	pubActor ! "Hi!!!"
  	pubActor ! "Hi!!!"
  	pubActor ! "Hi!!!"
  	pubActor ! "Hi!!!"
  	pubActor ! "Hi!!!"
  	pubActor ! "Hi!!!"
  	pubActor ! "Hi!!!"
  	pubActor ! "Hi!!!"
  	pubActor ! "Hi!!!"
  	pubActor ! "Hi!!!"*/
}