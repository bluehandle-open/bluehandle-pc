package com.whyun.util;

import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

public class ResourceUtil {
	public static ImageIcon getImageIcon(String relativePath) {
		ImageIcon icon = null;
		try {
			URL path= JarUtil.getResource(relativePath);
			if (path != null) {
				icon = new ImageIcon(path);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return icon;		
	}
}
