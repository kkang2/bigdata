package com.bigdata.akka.scala.http

import akka.stream.ActorMaterializer
import akka.actor.ActorSystem
import scala.util.Random
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpEntity
import akka.stream.scaladsl.Source
import akka.http.scaladsl.model.ContentTypes
import akka.util.ByteString
import akka.http.scaladsl.server.Directives._
import com.typesafe.config.ConfigFactory
import akka.actor.Props
import com.bigdata.akka.scala.cluster.actor.pubsub.SubscriberParamActor
import com.bigdata.akka.scala.cluster.actor.pubsub.PublisherParamActor
import com.typesafe.config.Config

object HttpDockerMain extends App {
	var config:Config = null
	var seedNodes:String = "["
	
	if(args.length == 3) {
		args(2).split("-").foreach(str => seedNodes = seedNodes + str + ",")
		
		seedNodes = seedNodes.substring(0, seedNodes.length()-1) + "]"
		
		config = ConfigFactory.parseResources("cluster_docker.conf")
													.withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.hostname=" + args(0)))
													.withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.port=" + args(1)))
													.withFallback(ConfigFactory.parseString("akka.cluster.seed-nodes=" + seedNodes))
	} else {
		config = ConfigFactory.parseResources("cluster_docker.conf")
	}
	
	println(config.getString("akka.remote.netty.tcp.hostname"))
	println(config.getString("akka.remote.netty.tcp.port"))
	println(config.getList("akka.cluster.seed-nodes"))
	
	implicit val system = ActorSystem("DockerActorSystem", config)
   implicit val materializer = ActorMaterializer()
   
   // needed for the future flatMap/onComplete in the end
   implicit val executionContext = system.dispatcher
   
   val subActor	= system.actorOf(Props(classOf[SubscriberParamActor], "contentTopic"), name="subActor") // create Actor
  	val pubActor	= system.actorOf(Props(classOf[PublisherParamActor], "contentTopic"), name="pubActor") // create Actor
   
   val route = path("sayHello") {
   	get {
   		pubActor ! "Docker cluster actor Hi!!!!!!"
      	complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Akka Actor 에게 Publish!!!</h1>"))
      }
	}

   val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8090)

   println(s"Server online at http://localhost:8090")
}