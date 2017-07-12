package org.bigdata.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.bigdata.spark.common.RDDUtil;
import org.bigdata.spark.common.SparkConfUtil;
import org.bigdata.spark.common.SparkContextUtil;

public class SimpleMain {
	public static void main(String[] args) {
		JavaSparkContext sc = SparkContextUtil.getContext(SparkConfUtil.getConf("SimpleApp", "local"));
		
		long time = System.currentTimeMillis();
		
		JavaRDD<String> contents = RDDUtil.getRddFromTextFile(sc, "src/main/resources/README.md");
		//JavaRDD<String> contents = sc.textFile("src/main/resources/README.md").cache();
		
		long numAs = contents.filter(new Function<String, Boolean>() {
	      public Boolean call(String s) { return s.contains("a"); }
	    }).count();

	    long numBs = contents.filter(new Function<String, Boolean>() {
	      public Boolean call(String s) { return s.contains("b"); }
	    }).count();

	    System.out.println("time : " + (System.currentTimeMillis() - time) + " Lines with a: " + numAs + ", lines with b: " + numBs);
	    
	    sc.stop();
	}
}