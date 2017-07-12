package org.bigdata.spark.common;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class RDDUtil {
	public static JavaRDD<String> getRddFromTextFile(JavaSparkContext sc, String path) {
		return sc.textFile(path);
	}
	
	public static JavaRDD<String> getRddFromList(JavaSparkContext sc, List<String> list) {
		return sc.parallelize(list);
	}
	
	public static void main(String[] args) {

	}

}
