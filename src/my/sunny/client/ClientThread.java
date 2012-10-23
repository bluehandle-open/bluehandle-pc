package my.sunny.client;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.StreamConnection;

import my.sunny.IBluetoothConst;
import my.sunny.communication.message.AbstractSendMessage;
import my.sunny.communication.message.MessageUtil;

public class ClientThread extends Thread implements IBluetoothConst {
	private StreamConnection socket;
	private OutputStream os;
	private InputStream is;
	private volatile boolean hasInited = false;
	private volatile static ClientThread instance = null;
	private Robot robot;
	
	private ClientThread() {
		
	}
	
	public static ClientThread getInstance() {
		if (instance == null) {
			synchronized (ClientThread.class) {
				if (instance == null) {
					instance = new ClientThread();
				}
			}
		}
		return instance;
	}
	
	public boolean isHasInit() {
		return hasInited;
	}

	public synchronized void initThread(StreamConnection socket) throws IOException, AWTException {
		
		this.socket = socket;
		os = socket.openOutputStream();
		is = socket.openDataInputStream();
		robot = new Robot();
		hasInited = MessageUtil.validServer(is);
//		if (hasInited) {
//			start();
//		}
		if (this.getState() == Thread.State.NEW) {
			start();
		} else {
			System.out.println("resart the thread.");
			this.interrupt();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			start();
		}
	}	
	
	public void run() {
		while(hasInited) {
			try {
				sleep(10);
				MessageUtil.processMessage(robot, is);
				System.out.println("process...");
			} catch (InterruptedException e) {				
				e.printStackTrace();
				if (socket != null) {
					stopServer();
				}
				return;
			} catch (IOException e) {				
				e.printStackTrace();
				if (socket != null) {
					stopServer();
				}
			} catch(NullPointerException e) {
				System.out.println("exit thread because server has existed.");
				e.printStackTrace();
				if (socket != null) {
					stopServer();
				}
			}
		}
	}
	
	public void send2Server(AbstractSendMessage message) throws IOException {
		if (hasInited) {
			os.write(message.getMessage());
			System.out.println("send a message.");
		}
	}
	
	public void stopServer() {
		if (hasInited) {
			try {
				this.interrupt();
				hasInited = false;
				is.close();
				os.close();
				socket.close();
				System.out.println("close the client thread.");
				instance = null;
			} catch (IOException e) {			
				e.printStackTrace();
			}
		}
	}	
}
