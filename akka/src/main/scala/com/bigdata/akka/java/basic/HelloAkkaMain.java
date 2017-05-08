package com.bigdata.akka.java.basic;

import akka.actor.AbstractActor;
import akka.actor.AbstractActor.Receive;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class HelloAkkaMain {
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("mqttRestServerActorSystem");
	}
}