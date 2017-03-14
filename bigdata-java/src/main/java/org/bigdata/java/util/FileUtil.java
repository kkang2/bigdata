package org.bigdata.java.util;

import java.io.BufferedReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {
	public static List<String> readAllLinesFromFile(Path pathObj) {
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(pathObj);
		} catch(Exception e) {
			e.printStackTrace();
			
			lines = Collections.<String>emptyList();
		}
		
		return lines;
	}
	
	public static List<String> readAllLinesFromFileBuffer(Path pathObj) {
		List<String> lines = new ArrayList<>();
		
		try(BufferedReader reader = Files.newBufferedReader(pathObj)) {
			String line = null;
			
			while((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return lines;
	}
	
	public static Path createFile(Path pathObj) {
		Path newPathObj = null;
		
		try {
			newPathObj = Files.createFile(pathObj);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return newPathObj;
	}
	
	public static Path getPathObj(String path) {
		return FileSystems.getDefault().getPath(path);
	}
	
	public static boolean isFileExist(Path pathObj, boolean isSymbolicAllow) {
		boolean result = false;
		
		if(isSymbolicAllow) {
			result = Files.exists(pathObj);
		} else {
			result = Files.exists(pathObj, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
		}
		
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		
		//System.out.println(Integer.parseInt("ff ll".split(" ")[0]));Integer.parseInt(first.split(" ")[0]) > Integer.parseInt(second.split(" ")[0])
		
		List<String> list = readAllLinesFromFile(Paths.get("C:\\Users\\PSJ\\git\\bigdata\\bigdata-java\\src\\main\\resources\\text2.txt"));
		
		list = list.stream().sorted((first, second) -> Integer.compare(Integer.parseInt(first.split(" ")[0]), Integer.parseInt(second.split(" ")[0]))).collect(Collectors.toList());
		
		for (String string : list) {
			System.out.println("string : " + string);
		}
		
		/*Path path = Paths.get("pom.xml");
		
		System.out.println(path.toRealPath(LinkOption.NOFOLLOW_LINKS));*/
	}
}
