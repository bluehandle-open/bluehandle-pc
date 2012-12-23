package com.whyun.util;

import java.net.URL;
//import java.util.logging.Logger;

public class ConfigFileUtil {
	private static final boolean isWin
		= System.getProperty("os.name").toUpperCase().substring(0,3).equals("WIN");
	
	public static String getPath(Class<?> classz,String filename) {
		URL url = classz.getClassLoader().getResource(filename);
		String configPath = null;
		if (url != null) {
			configPath = url.getPath().replaceAll("%20", " ");
//			logger.config("find file:"+configPath);
			if (isWin) {
				configPath = configPath.toLowerCase();
			}
		} else {
//			logger.info("not find "+ filename);
		}
		return configPath;
	}	
}
