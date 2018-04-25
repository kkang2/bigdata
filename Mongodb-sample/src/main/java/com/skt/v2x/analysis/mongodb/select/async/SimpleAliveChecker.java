package com.skt.v2x.analysis.mongodb.select.async;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.mongodb.rx.client.MongoClient;
import com.skt.v2x.analysis.mongodb.MongoAsyncUtil;

import rx.observers.TestSubscriber;

public class SimpleAliveChecker {

	public static void main(String[] args) throws Exception {
		//MongoClient client = MongoAsyncUtil.getAsyncClient("mongodb://psjuser:psjuser@221.132.71.203");
		MongoClient client = MongoAsyncUtil.getAsyncClient("mongodb://mgadmin:mgadmin123@221.132.71.203");
		//MongoClient client = MongoAsyncUtil.getAsyncClient("mongodb://@211.214.168.102:27033");
		
		TestSubscriber subscriber = new TestSubscriber();
		
		System.out.println(new Date());
		
		client.listDatabaseNames().subscribe(subscriber);
		
		subscriber.awaitTerminalEventAndUnsubscribeOnTimeout(500, TimeUnit.MILLISECONDS);
		
		System.out.println("Database count : " + subscriber.getValueCount());
		
		System.out.println(new Date());
		
		Thread.sleep(5000);
		
		//client.close();
	}
}
