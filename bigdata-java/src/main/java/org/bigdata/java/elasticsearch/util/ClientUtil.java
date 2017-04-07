package org.bigdata.java.elasticsearch.util;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientUtil {
	private static Logger logger = LoggerFactory.getLogger(ClientUtil.class);
	
	public static TransportClient createAndGetClient(String clusterName, List<InetSocketTransportAddress> addressList) {
		TransportClient client = null;
		
		try {
			Settings settings = Settings.builder()
					.put("cluster.name", clusterName)
					.put("client.transport.sniff", false)
					.put("client.transport.ping_timeout", 20, TimeUnit.SECONDS).build();
	        
			client = new PreBuiltTransportClient(settings).addTransportAddresses((InetSocketTransportAddress[])addressList.toArray());
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return client;
	}
	
	public static void main(String[] args) throws Exception {
		createAndGetClient("elasticsearch", Arrays.asList(new InetSocketTransportAddress(InetAddress.getByName("192.168.137.128"), 9300)));
	}
}
