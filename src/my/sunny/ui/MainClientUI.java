package my.sunny.ui;

import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;

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
	
	private static void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys
				.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		InitGlobalFont(new Font("宋体", Font.PLAIN, 14));
		MainClientUI start = new MainClientUI();
		start.start();
	}
}
