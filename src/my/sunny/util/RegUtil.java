package my.sunny.util;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {
	public static final  String nameReg = "^[a-zA-Z0-9\\-_\\.]{6,30}$";
	public static final String emailReg = "^[a-z0-9]+([._+-]*[a-z0-9])*@([a-z0-9][a-z0-9-]{0,61}[a-z0-9].){1,3}[a-z]{2,6}$";
	public static final String passReg = "^[\\w\\W]{6,10}$";
	public static final String mobileReg = "^(13|15|18)\\d{9}$";
	public static final String telReg = "^\\d{3,4}-?\\d{7,8}(-\\d{3,4})?$";//3-4位区号，7-8位直拨号�?3-4位分机号
	public static final String idCardReg = "^(\\d{14}|\\d{17})(\\d|[xX])$";//000000000000000(000)
	public static final String zipReg = "^\\d{6}$";
	public static final String ipReg = "/^((25[0-5]|2[0-4]\\d|1?\\d?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1?\\d?\\d)$/";
	public static final String simpleIpReg = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$";
	/**
	 * 将分割符转换为一维数组字符串
	 * 
	 * @param str
	 * @param sign
	 * @return
	 */
	public static String[] split(String str, String sign) {
		String[] strData = null;
		StringTokenizer st1 = new StringTokenizer(str, sign);
		strData = new String[st1.countTokens()];
		int i = 0;
		while (st1.hasMoreTokens()) {
			strData[i] = st1.nextToken().trim();
			i++;
		}
		return strData;
	}
	
	public static boolean isMathing(String reg,String input) {
		boolean result = false;
		if (null != reg && !reg.equals("") && input != null) {
			Pattern p =Pattern.compile(reg);
			Matcher m = p.matcher(input);
			result = m.matches();
		}
		return result;
	}
	public static boolean isNotNull(String str) {
		return str != null && !str.equals("");
	}
	public static boolean isValidUsername(String username) {
		return isMathing(nameReg,username);
	}
	public static boolean isNull(String str) {
		return str == null || "".equals(str);
	}
}
