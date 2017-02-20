package org.bigdata.java.util;

import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
	
	public static void main(String[] args) throws Exception {
		Path path = Paths.get("pom.xml");
		
		System.out.println(path.toRealPath(LinkOption.NOFOLLOW_LINKS));
	}
}
