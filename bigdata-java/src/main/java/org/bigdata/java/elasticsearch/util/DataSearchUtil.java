package org.bigdata.java.elasticsearch.util;

import java.net.InetAddress;
import java.util.Arrays;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;

public class DataSearchUtil {
	public static SearchResponse searchType1() throws Exception {
		TransportClient client = ClientUtil.createAndGetClient("elasticsearch", 
				Arrays.asList(new InetSocketTransportAddress(InetAddress.getByName("192.168.137.128"), 9300)));
		
		return client.prepareSearch("twitter")  // 인덱스 설정
		        .setTypes("tweet") // type 설정
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        //.setQuery(QueryBuilders.termQuery("user", "psj"))                 // Query
		        .setPostFilter(QueryBuilders.rangeQuery("age").from(10).to(19))     // Filter
		        .setFrom(0).setSize(60).setExplain(true)
		        .get();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(searchType1());
	}
}
