package my.sunny.util;

import java.io.IOException;

public final class CMDUtil {
	private CMDUtil() {
		
	}
	
	public static void executeFile(String filePath)
			throws IOException {
		Runtime.getRuntime().exec("cmd /c \""+filePath+"\"");
	}
}
