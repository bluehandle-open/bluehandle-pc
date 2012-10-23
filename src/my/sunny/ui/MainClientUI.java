package my.sunny.ui;

import java.awt.Container;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

public class MainClientUI {
	private JFrame frame = new JFrame("接入方式选择");
	
	private void addComponentsToPane(Container pane) {
		pane.setLayout(null);
		JPanel container = new JPanel();//
		container.setBounds(0,0,390,290);
		container.setBorder((TitledBorder)BorderFactory.createTitledBorder("请选择接入方式"));
		container.setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(10,80,380,50);
		
		ButtonGroup radios = new ButtonGroup();
		
		final JRadioButton wifiType = new JRadioButton("wifi方式");	
		mainPanel.add(wifiType);
		radios.add(wifiType);
		
		final JRadioButton bluetoothType = new JRadioButton("蓝牙方式");
		mainPanel.add(bluetoothType);
		radios.add(bluetoothType);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(200,150,190,100);
		
		JButton start = new JButton("开始");	
		bottomPanel.add(start);
		start.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				if (wifiType.isSelected()) {//使用wifi方式
					WifiClientUI.main(frame);
					frame.setVisible(false);
				} else if (bluetoothType.isSelected()) {//使用蓝牙方式
					BluetoothClientUI.main(frame);
					frame.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(frame,"请选择连接方式");
				}
			}
			
		});
		
		container.add(mainPanel);
		container.add(bottomPanel);
		pane.add(container);
	}
	
	public void start() {		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
	}
	
	public static void main(String argc[]) {
		MainClientUI start = new MainClientUI();
		start.start();
	}
}
