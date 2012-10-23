package my.sunny.communication.message;

public abstract class AbstractReciveMessage extends AbstractMessage {
	public AbstractReciveMessage(byte[] totalMessage) {
		if (totalMessage != null && totalMessage.length > 1) {
			this.totalLen = totalMessage[0];
			this.type = totalMessage[1];
			body = new byte[totalLen-2];
			if (totalMessage.length > 2) {
				try {
					System.arraycopy(totalMessage, 2, body, 0, totalLen-2);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
