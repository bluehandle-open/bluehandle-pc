package my.sunny.communication.message;

public class ReciveKey extends AbstractReciveMessage {
	private byte keyEventType = 0;
	private byte[] keyList;
	public ReciveKey(byte[] totalMessage) {
		super(totalMessage);
		init();
	}
	
	private void init() {
		int len = body.length;
		if (body != null && len > 0) {
			keyEventType = body[0];
			keyList = new byte[len-1];
			try {
				System.arraycopy(body, 1, keyList, 0, len-1);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public byte getKeyEventType() {
		return keyEventType;
	}

	public byte[] getKeyList() {
		return keyList;
	}
	
}
