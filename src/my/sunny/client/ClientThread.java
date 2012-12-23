package my.sunny.client;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import javax.microedition.io.StreamConnection;
import javax.swing.SwingUtilities;

import my.sunny.IBluetoothConst;
import my.sunny.communication.message.AbstractMessage;
import my.sunny.communication.message.HeartBeatRequestMessage;
import my.sunny.communication.message.MessageUtil;
import my.sunny.ui.BlueHandle;

public class ClientThread extends Thread implements IBluetoothConst {
	private StreamConnection socket;
	private OutputStream os;
	private InputStream is;
	private volatile boolean hasInited = false;
//	private volatile static ClientThread instance = null;
	private Robot robot;
	/**线程结束标志位，线程内部如果检测到stop为true的话，就退出*/
	private boolean stop;
	
	private BlueHandle parentFrame;
	
	public ClientThread() {
		stop = false;
	}
	
//	public static ClientThread getInstance() {
//		if (instance == null) {
//			synchronized (ClientThread.class) {
//				if (instance == null) {
//					instance = new ClientThread();
//				}
//			}
//		}
//		return instance;
//	}
	
	public boolean isHasInit() {
		return hasInited;
	}

	public synchronized void initThread(StreamConnection socket,BlueHandle parentFrame)
			throws IOException, AWTException {
		
		this.socket = socket;
		this.parentFrame = parentFrame;
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
			stop = true;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			stop = false;
			start();
		}
	}	
	
	public void run() {
		while(hasInited && !stop) {
			try {
				sleep(10);
				boolean isFinished = MessageUtil.processMessage(robot, is);
				if (!isFinished) {
					System.out.println("process...");
					send2Server(new HeartBeatRequestMessage());
				} else {
					if (socket != null) {
						stopServer("手机端退出");
					}
				}				
			} catch (InterruptedException e) {				
				e.printStackTrace();
				if (socket != null) {
					stopServer("出现异常，错误码1");
				}
				return;
			} catch (IOException e) {				
				e.printStackTrace();
				if (socket != null) {
					stopServer("出现异常，手机端可能已经退出，错误码2");
				}
			} catch(Exception e) {
				System.out.println("exit thread because server has existed.");
				e.printStackTrace();
				if (socket != null) {
					stopServer("出现手机端服务关闭等异常，错误码3");
				}
			}
		}
	}
	
	public void send2Server(AbstractMessage message) throws IOException {
		if (hasInited) {
			os.write(message.getMessage());
			System.out.println("send a message.");
		}
	}
	
	public void stopServer() {
		stopServer(null);
	}
	
	public void stopServer(String msg) {
		if (hasInited) {
			try {
				this.interrupt();
				hasInited = false;
				is.close();
				os.close();
				socket.close();
				System.out.println("close the client thread.");
//				instance = null;
				if (msg != null) {
					final String msgNow = msg;
					try {
						SwingUtilities.invokeAndWait(new Runnable() {			
							//@Override
							public void run() {
								parentFrame.showError(msgNow);
								parentFrame.showNotConnectedStatus();
							}							
						});
					} catch (InterruptedException e) {
						
					} catch (InvocationTargetException e) {
						
					}
				}
			} catch (IOException e) {			
				e.printStackTrace();
			}
		}
	}	
}
