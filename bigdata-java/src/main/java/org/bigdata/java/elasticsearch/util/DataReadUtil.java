package org.bigdata.java.elasticsearch.util;

import java.net.InetAddress;
import java.util.Arrays;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class DataReadUtil {
	public static GetResponse readData(String index, String type, String id) throws Exception {
		return ClientUtil.createAndGetClient("elasticsearch", Arrays.asList(new InetSocketTransportAddress(InetAddress.getByName("192.168.137.128"), 9300)))
			.prepareGet(index, type, id).get();
	}
	
	public static void main(String[] args) throws Exception {
		InfoPrinter.printIndexGetResponse(readData("twitter", "tweet", "2"));
	}
}
