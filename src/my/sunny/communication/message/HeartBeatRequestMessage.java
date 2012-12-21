package my.sunny.communication.message;

import my.sunny.IBluetoothConst;


public class HeartBeatRequestMessage extends AbstractMessage {

	public HeartBeatRequestMessage() {
		this.totalLen = 2;
		this.type = IBluetoothConst.HEART_BEAT_REQ;
		this.body = null;
//		super((byte)2, , null);
	}
}
