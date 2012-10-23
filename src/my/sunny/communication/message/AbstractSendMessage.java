package my.sunny.communication.message;

import my.sunny.IBluetoothConst;

public abstract class AbstractSendMessage  extends AbstractMessage implements IBluetoothConst {
	protected void printInfo(byte[] message) {
		if (message != null && message.length > 2) {
			System.out.println("the message's length is " + message[0]);
			System.out.println("the message's type is " + message[1]);
			for(int i=2;i<message.length;i++) {
				System.out.print(message[i] + " ");
			}
			System.out.println();
		}
	}
	/**
	 * get the whole message.used when send message.
	 * */
	public byte[] getMessage() {
		int len = 2+body.length+2;
		byte[] message = new byte[len];
		message[0] = totalLen;
		message[1] = type;
		message[len-1] = 10;//\n
		message[len-2] = 13;//\r
		
		System.arraycopy(body, 0, message, 2, body.length);
		if (isDebug) {
			printInfo(message);
		}
		return message;
	}
}
