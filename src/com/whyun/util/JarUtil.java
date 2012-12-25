package com.whyun.util;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class JarUtil {
	private String jarName;
	private JarFile jarFile;
	private String jarPath;
//	private String version;

	public JarUtil(Class<?> clazz) {
		String path = clazz.getProtectionDomain().getCodeSource().getLocation()
				.getFile();
		if (path != null) {
			try {
				path = java.net.URLDecoder.decode(path, "UTF-8");
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			java.io.File jarFile = new java.io.File(path);
			if (jarFile != null) {
				this.jarName = jarFile.getName();
				try {
					this.jarFile = new JarFile(jarName);
				} catch (IOException e) {
					e.printStackTrace();
				}
				java.io.File parent = jarFile.getParentFile();
				if (parent != null) {
					this.jarPath = parent.getAbsolutePath();
				}
			}		
		} else {
			System.out.println("获取jar路径失败");
		}
		
	}

	/**
	 * 获取Class类所在Jar包的名称
	 * 
	 * @return Jar包名 (例如：C:\temp\demo.jar 则返回 demo.jar )
	 */
	public String getJarName() {
		try {
			return java.net.URLDecoder.decode(this.jarName, "UTF-8");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取得Class类所在的Jar包路径
	 * 
	 * @return 返回一个路径 (例如：C:\temp\demo.jar 则返回 C:\temp )
	 */
	public String getJarPath() {
		try {
			return java.net.URLDecoder.decode(this.jarPath, "UTF-8");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getVersion() {
		String version = "";
		if (jarFile != null) {
			try {
				Manifest manifest = jarFile.getManifest();
				
				Attributes ats = manifest.getMainAttributes();
	            Set<?> set_ats = ats.keySet();
	            Iterator<?> it_ats = set_ats.iterator();
	            while (it_ats.hasNext()) {
	                String key = (String) it_ats.next();
	                if ("Implementation-Version".equals(key)) {
	                	version = (String)ats.get(key);
	                	System.out.println(key + " = " + version);
	                }
	            }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return version;
	}
	
	public static URL getResource(String relativePath) throws IOException{ 
        //查找指定资源的URL
		URL fileURL=JarUtil.class.getResource(relativePath);   
		
		return fileURL;
	}  

	public static void main(String[] args) throws Exception {
		JarUtil ju = new JarUtil(JarUtil.class);
		
		System.out.println("Jar path: " + ju.getJarPath());
	}
}