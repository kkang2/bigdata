package org.bigdata.spark.sql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.bigdata.spark.common.SparkConfUtil;
import org.bigdata.spark.common.SparkSessionUtil;

import static org.apache.spark.sql.functions.*;

public class JsonExample {

	public static void main(String[] args) {
		final String jsonPath = "src/main/resources/json";
		SparkSession spark = SparkSessionUtil.getSession(SparkConfUtil.getConf("SimpleApp", "local[*]"));
		StructType schema = new StructType().add("time", DataTypes.TimestampType).add("action", DataTypes.StringType);

		Dataset<Row> ds = 
			spark
				.readStream()
				.schema(schema)
				.option("maxFilesPerTrigger", 1) // Treat a sequence of files as a stream by picking one file at a time
				.json(jsonPath);
		
		System.out.println("isStreaming : " + ds.isStreaming());
		
		Dataset<Row> countDs = ds.groupBy(ds.col("action"), window(ds.col("time"), "10 minutes")).count();
		
		countDs.writeStream()
			.format("memory")
			.queryName("counts")
			.outputMode("complete")
			.start();
		
		
		/*Dataset<Row> ds = spark.read().schema(schema).json("src/main/resources/action.json");
		ds.groupBy(ds.col("action"), window(ds.col("time"), "10 minutes")).count().show();*/
	}
}