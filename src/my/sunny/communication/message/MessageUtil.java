package my.sunny.communication.message;

import java.awt.Robot;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import my.sunny.IBluetoothConst;
import my.sunny.util.KeyProcessUtil;

public class MessageUtil implements IBluetoothConst {
	private MessageUtil() {
		
	}
	public static AbstractMessage parse(byte[] totalMessge) {
		AbstractMessage message = null;
		if (totalMessge != null && totalMessge.length > 1) {
			byte type = totalMessge[0];
			//byte totalLen = totalMessge[0];
			if (type == recieveKey) {//��������¼�
				message = new ReciveKey(totalMessge);
			} else if (type == FINISH_SOCKET) {
				message = new FinishMessage();
			} else {
				System.out.println("unsupport message type:"+type);
			}
		}
		return message;
	}
	
	/**
	 * 检查是否是合法的服务器
	 * 
	 * @param is 输入流
	 * 
	 */
	public static boolean validServer(InputStream is) {
		boolean hasInited = false;
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));			
			
			String beginMsg = reader.readLine();
			System.out.println(beginMsg);
			if (IBluetoothConst.serverSign.equals(beginMsg)) {				
				//os.write(clientConnectResponse.getBytes()); 
				System.out.println("is a valid server.");
				hasInited = true;
			} else {
				System.out.println("not a valid server.");
			}
			
		} catch (IOException e) {			
			e.printStackTrace();			
		}
		return hasInited;
	}

	public static boolean processMessage(Robot robot,InputStream is)
		throws IOException {
		boolean isFinished = false;
//		BufferedReader BufferedReader = new BufferedReader(new InputStreamReader(is));
		//String messageStr = brd.readLine();
		DataInputStream in = new DataInputStream(is);


		byte type = in.readByte();
		AbstractMessage message = null;
		switch(type) {
		case recieveKey:
			
			byte totalLen = in.readByte();
			if (totalLen > 0) {
				byte[] totalMessge = new byte[totalLen];
				totalMessge[0] = type;
				totalMessge[1] = totalLen;
				in.read(totalMessge, 2, totalLen-2);
				message = new ReciveKey(totalMessge);
				System.out.println("get a key event.");
				KeyProcessUtil.fireKeyEvent(robot,(ReciveKey)message);
			}
			break;
		case FINISH_SOCKET:
			in.readByte();//消耗掉头部的长度字节
			isFinished = true;
			message = new FinishMessage();
			break;
		default:
			System.out.println("unknown message type:" + type);
			break;
		}

		//brd.re
		System.out.println();
		
		return isFinished;
	}
}
