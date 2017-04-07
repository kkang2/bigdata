package org.bigdata.java.elasticsearch.util;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.rest.RestStatus;

public class InfoPrinter {
	public static void printIndexResponseInfo(IndexResponse response) {
		String index 	= response.getIndex(); 		// Index name
		String type 	= response.getType(); 		// Type name
		String id 		= response.getId(); 			// Document ID (generated or not)
		
		long version 	= response.getVersion(); 	// Version (if it's the first time you index this document, you will get: 1)
		
		RestStatus status = response.status(); 	// status has stored current instance statement.
		
		System.out.println("index : " + index);
		System.out.println("type : " + type);
		System.out.println("id : " + id);
		System.out.println("version : " + version);
		System.out.println("status : " + status);
	}
	
	public static void printUpdateResponseInfo(UpdateResponse response) {
		String index 	= response.getIndex(); 		// Index name
		String type 	= response.getType(); 		// Type name
		String id 		= response.getId(); 			// Document ID (generated or not)
		
		long version 	= response.getVersion(); 	// Version (if it's the first time you index this document, you will get: 1)
		
		RestStatus status = response.status(); 	// status has stored current instance statement.
		
		System.out.println("index : " + index);
		System.out.println("type : " + type);
		System.out.println("id : " + id);
		System.out.println("version : " + version);
		System.out.println("status : " + status);
	}
	
	public static void printIndexGetResponse(GetResponse response) {
		String index 	= response.getIndex(); 				// Index name
		String type 	= response.getType(); 				// Type name
		String id 		= response.getId(); 					// Document ID (generated or not)
		String source	= response.getSourceAsString();
		
		long version 	= response.getVersion(); 	// Version (if it's the first time you index this document, you will get: 1)
		
		System.out.println("index : " + index);
		System.out.println("type : " + type);
		System.out.println("id : " + id);
		System.out.println("version : " + version);
		System.out.println("source : " + source);
	}
	
	public static void main(String[] args) {

	}

}
