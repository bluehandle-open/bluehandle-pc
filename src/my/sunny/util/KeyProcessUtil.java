package my.sunny.util;

import java.awt.Robot;

import my.sunny.IBluetoothConst;
import my.sunny.communication.message.ReciveKey;

public class KeyProcessUtil {
	/**
	 * 触发按键事件
	 * */
	public static void fireKeyEvent(Robot robot,ReciveKey key) {
		byte[] msg = key.getKeyList();
		if(msg != null) {
			int len = msg.length;
			if (len > 0) {
				byte pressType = key.getKeyEventType();
				System.out.println("the press type is " + pressType);
				if ((pressType & IBluetoothConst.toPress) > 0) {
					for (int i=0;i<len;i++) {
						int keyCode = msg[i];
//						System.out.println("orginal data " + keyCode);
						if (keyCode < 0) {
							keyCode &= 0xff;
						}
						if (keyCode != 0) {
							System.out.println("press " + keyCode);
							try {
								robot.keyPress(keyCode);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					}
				}
			
				if ((pressType & IBluetoothConst.toRelease) > 0) {
					for (int i=0;i<len;i++) {
						int keyCode = msg[i];
//						System.out.println("orginal data " + keyCode);
						if (keyCode < 0) {
							keyCode &= 0xff;
						}
						if (keyCode != 0) {
							try {
								robot.keyRelease(keyCode);
							} catch (Exception e) {
								e.printStackTrace();
							}
							System.out.println("release " + keyCode);
						}
						
					}
				}
			} else {
				System.out.println("the key message is empty.");
			}
		} else {
			System.out.println("the key message is null.");
		}
	}
	
	
}
