package com.skt.v2x.analysis.mongodb.select.async;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.exclude;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import com.skt.v2x.analysis.mongodb.MongoAsyncUtil;

import rx.functions.Action1;

public class SimpleSelect {

	public static void main(String[] args) throws Exception {
		//MongoClient client = MongoAsyncUtil.getAsyncClient("mongodb://psjuser:psjuser@221.132.71.203");
		MongoClient client = MongoAsyncUtil.getAsyncClient("mongodb://mgadmin:mgadmin123@221.132.71.203");
		MongoDatabase database = client.getDatabase("mydatabase");
		MongoCollection<Document> coll = database.getCollection("roadCollection");
		
		SingleResultCallback<Void> callbackWhenFinished = new SingleResultCallback<Void>() {
		    public void onResult(final Void result, final Throwable t) {
		        System.out.println("result : " + result);
		        System.out.println("Throwable : " + t);
		    }
		};
		
		Block<Document> printBlock = new Block<Document>() {
		    public void apply(final Document document) {
		        System.out.println(document.toJson());
		    }
		};
		
		/*
		 * equal 조건과 특정필드 제외하는 구문
		 * */
		coll.find(eq("linkCate", 2)).projection(exclude("roadId"))
			.toObservable().forEach(new Action1<Document>() {
				public void call(Document arg0) {
					System.out.println(arg0);
				}
			});
		
		Thread.sleep(5000);
		
		//client.close();
		
	}
}
