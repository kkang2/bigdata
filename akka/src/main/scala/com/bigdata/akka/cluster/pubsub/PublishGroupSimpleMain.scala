package com.bigdata.akka.cluster.pubsub

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.actor.Props
import com.bigdata.akka.cluster.actor.pubsub.PublisherGroupActor
import com.bigdata.akka.cluster.actor.pubsub.SubscriberGroupActor

/*
 * conf 파일에 아래와 같이 넣어주면 DistributedPubSub 로딩이 늦어져서 기능이 수초후에 작동되는 것을 방지함.
 * akka.extensions = ["akka.cluster.pubsub.DistributedPubSub"]
 **/
object PublishGroupSimpleMain extends App {
	val system = ActorSystem("ClusterSystem", ConfigFactory.parseResources("cluster.conf")) // create ActorSystem
  	
	val subGroupActor1	= system.actorOf(Props[SubscriberGroupActor], "subGroupActor1") // create Actor
  	val subGroupActor2	= system.actorOf(Props[SubscriberGroupActor], "subGroupActor2") // create Actor
  	
  	val pubGroupActor = system.actorOf(Props[PublisherGroupActor], "pubGroupActor") // create Actor
  	
  	pubGroupActor ! "Hi Group!!"
}