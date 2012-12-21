package my.sunny;

public interface IBluetoothConst {
	public static String uuid  = "0000110100001000800000805F9B34FB";
	public static boolean isDebug = true;	
	public static final String serverSign = "MyBluetoothApp";
	public static final String clientConnectResponse = "hello server\r\n";
	
	public static final byte recieveServerSign = 0x01;
	public static final byte recieveKey = 0x02;
	
	public static final byte handleKeySet = 0x10;	
	//public static final byte handleKeyGet = 0x11;
	public static final byte pptKeySet = 0x20;
	//public static final byte pptKeyGet = 0x21;
	public static final byte playerKeySet = 0x30;
	//public static final byte playerKeyGet = 0x31;
	public static final byte FINISH_SOCKET = 0x40;
	public static final byte HEART_BEAT_REQ = 0x41;
	public static final byte HEART_BEAT_RESP = 0x42;
	
	public static final byte toPress = 1;
	public static final byte toRelease = 2;
	//public static final byte toPreassRelease = 3;
	public static final int PORT = 8088;
	public static final int WIFI_TYPE = 0;
	public static final int BLUETOOTH_TYPE = 1;
}
