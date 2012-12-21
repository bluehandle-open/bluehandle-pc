package my.sunny.communication.message;

/**
 * The Class AbstractMessage.
 */
public abstract class AbstractMessage {
	
	/** The total message length. */
	protected byte totalLen;
	
	/** The message type. */
	protected byte type;
	
	/** The message content body. */
	protected byte[] body;
	
	public byte getTotalLen() {
		return totalLen;
	}

	public byte getType() {
		return type;
	}

	public byte[] getBody() {
		return body;
	}
	
	public byte[] getMessage() {
		byte[] data = new byte[totalLen];
		data[0] = totalLen;
		data[1] = type;
		if (body != null) {
			System.arraycopy(body, 0, data, 2, body.length);
		}
		
		return data;
	}
}
