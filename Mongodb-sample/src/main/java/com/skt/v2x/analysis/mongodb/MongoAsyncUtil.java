package com.skt.v2x.analysis.mongodb;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;

public class MongoAsyncUtil {
	public static MongoClient getAsyncClient(String connectionString) {
		return MongoClients.create(connectionString);
	}
	
	public static void main(String[] args) {

	}

}
