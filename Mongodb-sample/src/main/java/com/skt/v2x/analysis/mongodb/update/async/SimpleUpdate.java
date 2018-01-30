package com.skt.v2x.analysis.mongodb.update.async;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import com.skt.v2x.analysis.mongodb.MongoAsyncUtil;

import rx.functions.Action1;

public class SimpleUpdate {

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
		
		/*비동기 이기 때문에 메소드 호출 후 기다리는 작업이 필요한듯 함*/
		//Observable updateResult = coll.updateMany(eq("meshId", "4265"), new Document("$set", new Document("linkId", "1249")));
		/*coll.updateMany(eq("meshId", "4265"),
				new Document("$set", new Document("linkId", "1249"))).subscribe(new Action1<UpdateResult>() {
					public void call(UpdateResult u) {
						System.out.println("호출");
					}
				});*/
		
		coll.updateMany(eq("meshId", "4265"),
				new Document("$set", new Document("linkId", "1250")));
		
		coll.find().toObservable().forEach(new Action1<Document>() {
			public void call(Document arg0) {
				System.out.println(arg0);
			}
		});
		
		Thread.sleep(10000);
		
		//client.close();
	}
}
