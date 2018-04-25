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

import rx.Subscriber;
import rx.functions.Action1;

public class SimpleSelectSubscribe {

	public static void main(String[] args) throws Exception {
		//MongoClient client = MongoAsyncUtil.getAsyncClient("mongodb://psjuser:psjuser@221.132.71.203");
		//MongoClient client = MongoAsyncUtil.getAsyncClient("mongodb://mgadmin:mgadmin123@221.132.71.203");
		//mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database.collection][?options]]
		MongoClient client = MongoAsyncUtil.getAsyncClient("mongodb://mgadmin:mgadmin123@211.214.168.102:27033/v2x.road_info");
		MongoDatabase database = client.getDatabase("v2x");
		MongoCollection<Document> coll = database.getCollection("road_info");
		
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
		
		coll.find(eq("roadId", "187363744")).subscribe(new Subscriber<Document>() {
		//coll.find(eq("roadId", "42651248")).subscribe(new Subscriber<Document>() {
			@Override
			public void onStart() {
				System.out.println("호출1");
				super.onStart();
			}
			
			public void onCompleted() {
				System.out.println("호출2");
			}
			
			public void onError(Throwable e) {
				System.out.println("호출3");
			}
			
			public void onNext(Document t) {
				System.out.println("호출4");
			}
		});
		
		Thread.sleep(5000);
		
		client.close();
		
	}
}
