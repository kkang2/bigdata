package org.bigdata.java;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		/*System.out.println(Arrays.asList(1,2,3).stream().allMatch((num) -> num < 4));
		System.out.println(Arrays.asList(1,2,3).stream().anyMatch((num) -> num == 1));
		System.out.println(Arrays.asList(1,2,3).stream().noneMatch((num) -> num > 4));
		
		System.out.println(Arrays.asList(1,2,3).stream().reduce((num1, num2) -> num1 + num2));
		System.out.println(ZoneId.getAvailableZoneIds());*/
		
		/*List<String> words = Arrays.asList("java","scala","c","pascal","python");
		
		System.out.println(words.stream()
				.map(word->word.split("")).map(Arrays::stream).collect(Collectors.toList()));
		
		String[] arr = "java".split("");
		
		for (String string : arr) {
			System.out.println(string);
		}*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println(sdf.format(new Date()));
	}
}