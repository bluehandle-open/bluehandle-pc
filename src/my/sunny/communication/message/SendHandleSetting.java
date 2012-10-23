package my.sunny.communication.message;

import my.sunny.IBluetoothConst;
import my.sunny.communication.KeySetting;

public class SendHandleSetting extends AbstractSendMessage {
	
	public SendHandleSetting() {
		this.type = IBluetoothConst.handleKeySet;
		this.totalLen = 14;
		this.body = KeySetting.handleKeys;
	}	
}
