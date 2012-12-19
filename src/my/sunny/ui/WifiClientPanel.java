package my.sunny.ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import my.sunny.client.WifiSocket;
import my.sunny.util.RegUtil;

public class WifiClientPanel extends AbstractTabPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4618648772085518640L;
	private JLabel message;
	private JButton start;
	private JTextField ip;
	private JLabel inputInfo;

	private WifiSocket socketNow;

	public WifiClientPanel(int width, int height, BlueHandle parentFrame) {
		super(width, height, parentFrame);		
	}

	@Override
	protected void init() {
	
		JPanel container = new JPanel();//
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		JPanel inputArea = new JPanel();
		
		inputInfo = new JLabel("请输入手机端的ip");
		inputArea.add(inputInfo);
		ip = new JTextField("", 15);
		inputArea.add(ip);
		start = new JButton("开始");
		inputArea.add(start);

		container.add(inputArea);

		add(container,BorderLayout.PAGE_START);
	}

	@Override
	protected void disableUI() {
		inputInfo.setEnabled(false);
		start.setEnabled(false);
		ip.setEnabled(false);
	}

	@Override
	protected void enableUI() {
		inputInfo.setEnabled(true);
		start.setEnabled(true);
		ip.setEnabled(true);
	}

	@Override
	protected void addEventListener() {
		start.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				String ipStr = ip.getText();
				System.out.println("ipNow:"+ipStr);
				if (RegUtil.isMathing(RegUtil.simpleIpReg, ipStr)) {
					parentFrame.showInfo("正在连接……");
					if (socketNow != null) {
						socketNow.stopServer();
					}
					
					parentFrame.showConnStatus("未连接");
					try {
						socketNow = new WifiSocket(ipStr, message);
						socketNow.start();
						
						parentFrame.showSuccess("初始化成功");
						parentFrame.showConnStatus("已连接");
						parentFrame.setConnectedType(BlueHandle.CONN_TYPE_WIFI);
					} catch (IOException e1) {
						e1.printStackTrace();
						
						parentFrame.showError("初始化失败，错误码1");
					} catch (AWTException e1) {
						e1.printStackTrace();
						
						parentFrame.showError("初始化失败，错误码2");
					}
				} else {
					
					parentFrame.showError("IP地址格式错误");
					System.out.println("IP地址格式错误");
				}
			}

		});

	}

	@Override
	protected void close() {
		if (socketNow != null) {
			socketNow.stopServer();
		}
	}

}
