package com.skt.v2x.analysis.mongodb;

import com.mongodb.MongoClient;

public class MongoSyncUtil {
	public static MongoClient getSyncClient(String host, int port) {
		return new MongoClient(host, port);
	}
	
	public static void main(String[] args) {

	}

}
