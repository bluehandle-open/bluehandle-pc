package my.sunny.ui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

public class WifiClientUI {
	private JFrame frame = new JFrame("WIFI接入方式");
	private JFrame parent;
	
	public WifiClientUI(JFrame parent) {
		this.parent = parent;
	}
	
	private void addComponentsToPane(Container pane) {
		JPanel container = new JPanel();//
		container.setLayout(null);
		
		JPanel inputArea = new JPanel();
		inputArea.setBounds(0, 0, 400, 50);
		
		JLabel inputInfo = new JLabel("请输入手机端的ip");
		inputArea.add(inputInfo);
		
		final JTextField ip = new JTextField("",15);		
		inputArea.add(ip);
		
		JButton start = new JButton("开始");	
		inputArea.add(start);
		
		container.add(inputArea);
		
		JPanel messageArea = new JPanel();
		messageArea.setBounds(0, 100, 390, 100);
		//messageArea.setLayout(null);
		messageArea.setBorder((TitledBorder)BorderFactory.createTitledBorder("提示信息"));
				
		final JLabel message = new JLabel("");
		messageArea.add(message);
		
		container.add(messageArea);
		
		start.addActionListener(new ActionListener() {

			//@Override
			public void actionPerformed(ActionEvent e) {
				String ipStr = ip.getText();
				if (RegUtil.isMathing(RegUtil.simpleIpReg, ipStr)) {
					message.setText("正在连接……");
					try {
						new WifiSocket(ipStr,null).start();//这个地方不能用，因为传递过去的是空指针
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
		
		pane.add(container);
	}
	
	public void start() {
		
		// Set up the content pane.
		addComponentsToPane(frame.getContentPane());
		// Display the window.
		frame.pack();
		frame.setSize(400, 300);
		double   width   =   Toolkit.getDefaultToolkit().getScreenSize().getWidth(); 
        double   height   =   Toolkit.getDefaultToolkit().getScreenSize().getHeight(); 
        frame.setLocation(   (int)   (width   -   frame.getWidth())   /   2, 
                                 (int)   (height   -   frame.getHeight())   /   2); 
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {
				parent.setVisible(true);				
			}
		});
	}
	
	public static void main(JFrame parent) {
		new WifiClientUI(parent).start();
	}
}
