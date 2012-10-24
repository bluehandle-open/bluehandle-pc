package my.sunny.communication.message;

import my.sunny.IBluetoothConst;



public class FinishMessage extends AbstractMessage {

	public FinishMessage() {
		this.totalLen = 2;
		this.type = IBluetoothConst.FINISH_SOCKET;
		this.body = null;
	}

}
