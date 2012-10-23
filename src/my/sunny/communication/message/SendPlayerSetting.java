package my.sunny.communication.message;

import my.sunny.IBluetoothConst;
import my.sunny.communication.KeySetting;

public class SendPlayerSetting extends AbstractSendMessage implements IBluetoothConst {
	public SendPlayerSetting() {
		this.totalLen = 20;
		this.type = playerKeySet;
		this.body = KeySetting.playerKeys;
	}
	
}
