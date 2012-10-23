package my.sunny.client;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import my.sunny.IBluetoothConst;
import my.sunny.communication.message.MessageUtil;

public class WifiSocket extends Thread {
	private Socket socket = null;
	private OutputStream os;
	private InputStream is;
	private boolean stop = false;
	private Robot robot;
	private JLabel messageArea;
	public WifiSocket(String phoneIp,JLabel messageArea) throws IOException, AWTException {
		this.socket = new Socket(phoneIp,IBluetoothConst.PORT);
		this.os = socket.getOutputStream();
		this.is = socket.getInputStream();
		this.robot = new Robot();
		this.messageArea = messageArea;
	}
	
	public void run() {
		while(!stop) {
			if (socket != null && is != null) {
				try {
					MessageUtil.processMessage(robot, is);
					System.out.println("process...");
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
					close("出现异常，错误码1");
				} catch (IOException e) {
					e.printStackTrace();
					close("出现异常，错误码2");
				} catch (Exception e) {//出现手机端服务关闭等异常
					e.printStackTrace();
					stopServer("出现手机端服务关闭等异常，错误码3");
				}
			}
		}
	}
	
	private void close(String msg) {
		if (socket != null) {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
				socket.close();	
				if (msg != null) {
					final String msgNow = msg;
					try {
						SwingUtilities.invokeAndWait(new Runnable() {			
							//@Override
							public void run() {
								messageArea.setText(msgNow);
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
		
	public void stopServer() {
		stopServer(null);
	}
	
	public void stopServer(String msg) {
		close(msg);
		stop = true;
	}
}
