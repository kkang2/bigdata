package org.bigdata.spark;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.explode;
import static org.apache.spark.sql.functions.split;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.bigdata.spark.common.SparkConfUtil;
import org.bigdata.spark.common.SparkSessionUtil;

public class SqlMain {

	public static void main(String[] args) {
		SparkSession session = SparkSessionUtil.getSession(SparkConfUtil.getConf("SimpleApp", "local"));
		
		Dataset<Row> ds = session.read().text("src/main/resources/README.md");
		Dataset<Row> wordDF = ds.select(explode(split(col("value"), " ")).as("word"));
	    Dataset<Row> result = wordDF.groupBy("word").count();
	    result.show();
	}
}
