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
}
