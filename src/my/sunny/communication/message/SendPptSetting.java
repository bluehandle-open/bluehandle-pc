package my.sunny.communication.message;

import my.sunny.IBluetoothConst;
import my.sunny.communication.KeySetting;

public class SendPptSetting extends AbstractSendMessage  implements IBluetoothConst {
	public SendPptSetting() {
		this.totalLen = 9;
		this.type = pptKeySet;
		this.body = KeySetting.pptKeys;
	}
	

}
