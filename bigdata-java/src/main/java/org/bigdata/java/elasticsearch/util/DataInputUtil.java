package org.bigdata.java.elasticsearch.util;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

public class DataInputUtil {
	public static IndexResponse insertData(String index, String type, String id, XContentBuilder data) throws Exception {
		return ClientUtil.createAndGetClient("elasticsearch", Arrays.asList(new InetSocketTransportAddress(InetAddress.getByName("192.168.137.128"), 9300)))
			.prepareIndex(index, type, id)
			.setSource(data)
			.get();
	}
	
	public static UpdateResponse updateData(String index, String type, String id, XContentBuilder data) throws Exception {
		return ClientUtil.createAndGetClient("elasticsearch", Arrays.asList(new InetSocketTransportAddress(InetAddress.getByName("192.168.137.128"), 9300)))
				.prepareUpdate(index, type, id)
				.setDoc(data)
				.get();
	}
	
	public static void main(String[] args) throws Exception {
		XContentBuilder data = jsonBuilder()
				.startObject()
				.field("user", "psj1")
				.field("postDate", new Date())
				.field("message", "I'm psj1")
				.field("age", "20")
				.endObject();
		
		/*XContentBuilder data = jsonBuilder()
				.startObject()
				.field("age", "18")
				.endObject();*/
		
		InfoPrinter.printIndexResponseInfo(insertData("twitter", "tweet", "1", data));
		//InfoPrinter.printUpdateResponseInfo(updateData("twitter", "tweet", "3", data));
	}
}
