package com.whyun.util;

import java.io.File;


/**
 *
 */
public class FileFindUtil {
//	private static final Logger logger = GlobalData.log;
	/**
	 * 在class文件所在根目录、当前目录、 jar所在的目录找文件，
	 * 如果找到返回文件路径，否则返回null
	 * @return
	 */
	public static String find(String filename) {		

		//src所在目录
		String path = ConfigFileUtil.getPath(FileFindUtil.class, filename);
		
		if(path != null && new File(path).exists()) {
			return path;
		}
		
		/* 当前目录 */
		path =  filename;
		if(new File(path).exists()) {
			return path;
		} 		
		
		/* JAR包所在的目录 */
		JarUtil util = new JarUtil(JarUtil.class);
		path = util.getJarPath()+"/" + filename;
		if(new File(path).exists()) {
			return path;
		}
		
		return null;
	}
	

		

}
