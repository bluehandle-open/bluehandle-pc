package my.sunny.communication;

public class KeySetting {
	public static byte[] handleKeys = new byte[]{
		87,//w
		83,//s
		65,//a
		68,//3
		85,//u
		73,//i
		79,//o
		74,//j
		75,//k
		76,//l
		49,//1
		53//5
	};
	public static byte[] pptKeys = new byte[]{
		38,//up
		40,//down
		37,//left
		39,//right
		27,//esc->escape
		16,116//shift,F5->open
	};
	public static byte[] playerKeys = new byte[]{
		17,18,116,//ctrl+alt+F5->play/pause
		17,18,117,//ctrl+alt+F6->stop
		17,18,37,//ctrl+alt+left->previous
		17,18,39,//ctrl+alt+right->next
		17,18,38,//ctrl+alt+up->increase volume
		17,18,40//ctrl+alt+down->decrease volume
	};
	
	private KeySetting() {		
	}
	
//	public static byte[] getHandleKeys() {
//		return handleKeys;
//	}
//	public static void setHandleKeys(byte[] handleKeys) {
//		KeySetting.handleKeys = handleKeys;
//	}
//	public static byte[] getPptKeys() {
//		return pptKeys;
//	}
//	public static void setPptKeys(byte[] pptKeys) {
//		KeySetting.pptKeys = pptKeys;
//	}
//	public static byte[] getPlayerKeys() {
//		return playerKeys;
//	}
//	public static void setPlayerKeys(byte[] playerKeys) {
//		KeySetting.playerKeys = playerKeys;
//	}	
}
