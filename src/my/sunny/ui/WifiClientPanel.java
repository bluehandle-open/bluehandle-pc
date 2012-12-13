package my.sunny.ui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import my.sunny.client.WifiSocket;
import my.sunny.util.RegUtil;

public class WifiClientPanel extends AbstractTabPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4618648772085518640L;
	private JLabel message = new JLabel("");
	private JButton start = new JButton("开始");
	private JTextField ip = new JTextField("", 15);

	private WifiSocket socketNow;

	public WifiClientPanel(int width, int height, JFrame parentFrame) {
		super(width, height, parentFrame);		
	}

	@Override
	protected void init() {
		this.setLayout(null);
		this.setBounds(1, 1, width-2, height-2);

		JPanel container = new JPanel();//
		container.setLayout(null);
		container.setBounds(1, 1, width-2, height-2);		

		JPanel inputArea = new JPanel();
		inputArea.setBounds(0, 0, 400, 50);

		JLabel inputInfo = new JLabel("请输入手机端的ip");
		inputArea.add(inputInfo);

		
		start = new JButton("开始");
		ip = new JTextField("", 15);
		inputArea.add(ip);

		inputArea.add(start);

		container.add(inputArea);

		JPanel messageArea = new JPanel();
		messageArea.setBounds(0, 100, 390, 100);
		// messageArea.setLayout(null);
		messageArea.setBorder((TitledBorder) BorderFactory
				.createTitledBorder("提示信息"));
		message = new JLabel("");
		messageArea.add(message);

		container.add(messageArea);

		add(container);
	}

	@Override
	protected void addEventListener() {
		start.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				String ipStr = ip.getText();
				if (RegUtil.isMathing(RegUtil.simpleIpReg, ipStr)) {
					message.setText("正在连接……");
					if (socketNow != null) {
						socketNow.stopServer();
					}
					try {
						socketNow = new WifiSocket(ipStr, message);
						socketNow.start();
						message.setForeground(Color.BLACK);
						message.setText("初始化成功");
					} catch (IOException e1) {
						e1.printStackTrace();
						message.setForeground(Color.RED);
						message.setText("初始化失败，错误码1");
					} catch (AWTException e1) {
						e1.printStackTrace();
						message.setForeground(Color.RED);
						message.setText("初始化失败，错误码2");
					}
				} else {
					message.setForeground(Color.RED);
					message.setText("IP地址格式错误");
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
