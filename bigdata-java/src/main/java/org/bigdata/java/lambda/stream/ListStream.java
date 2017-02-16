package org.bigdata.java.lambda.stream;

import java.util.Arrays;

public class ListStream {
	public static void main(String[] args) {
		// forEach
		/*Arrays.asList(1,2,3).stream()
									.forEach(System.out::println);*/
		
		// map
		/*Arrays.asList(1,2,3).stream()
									.map(i -> i * i)
									.forEach(System.out::println);*/
		
		// limit
		/*Arrays.asList(1,2,3).stream()
									.limit(1)
									.forEach(System.out::println);*/
		
		// skip
		/*Arrays.asList(1,2,3).stream()
									.skip(1)
									.forEach(System.out::println);*/
		
		// filter
		/*Arrays.asList(1,2,3).stream()
									.filter(i -> i <= 2)
									.forEach(System.out::println);*/
		
		// flatMap
		/*Arrays.asList(Arrays.asList(1,2), Arrays.asList(3,4,5), Arrays.asList(6,7,8,9)).stream()
			.flatMap(i -> i.stream())
			.forEach(System.out::println); // 1,2,3,4,5,6,7,8,9 */		
		
		// reduce
		int num = Arrays.asList(1,2,3).stream()
									.reduce((a,b)-> a-b)
									.get();
		
		System.out.println(num);
									
	}
}
