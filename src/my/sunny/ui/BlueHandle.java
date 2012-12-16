package my.sunny.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;

public class BlueHandle extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6761264863876829858L;

	private static final int CONN_TYPE_BLUE = 1;
	private static final int CONN_TYPE_WIFI = 0;
	JTextArea theArea = null;
	private static final int DEFAULT_WIDTH = 620;
	private static final int DEFAULT_HEIGHT = 520;
	private int selectedType;

	private static final String CMD_SELECT_BLUE_CONN = "selectBlueConnect";
	private static final String CMD_SELECT_WIFI_CONN = "selectWifiConnect";
	private static final String CMD_SHOW_MANUAL = "showManual";
	private static final String CMD_SHOW_ABOUT = "showAbout";
	private static final String CMD_EXIT = "exit";
	
	private JButton selectWifiConn;
	private JButton selectBlueConn;
	
	private AbstractTabPanel wifiTab;
	private AbstractTabPanel blueTab;
	
	private JTabbedPane tabs;

	public BlueHandle() {

		super("蓝色手柄");
		// theArea = new JTextArea();
		// theArea.setEditable(false);
		// getContentPane().add(new JScrollPane(theArea));
		addComponentsToPane(getContentPane());		
	}

	private void addComponentsToPane(Container pane) {
		tabs = new JTabbedPane();//tab区

		int realWidth = DEFAULT_WIDTH - 10;
		int realHeight = DEFAULT_HEIGHT - 50;

		wifiTab = new WifiClientPanel(realWidth, realHeight, this);//

		tabs.addTab("wifi连接", wifiTab);

		blueTab = new BluetoothClientPanel(realWidth, realHeight,
				this);//

		tabs.add("蓝牙连接", blueTab);
		tabs.setEnabledAt(0, false);
		//wifiTab.setEnabled(false);
		tabs.setEnabledAt(1, false);
		pane.add(tabs);
		wifiTab.disableUI();
		System.out.println("wifiTab:"+wifiTab.getPreferredSize()+":"+wifiTab.getLocation());
		
		JMenuBar MBar = new JMenuBar();
		MBar.setOpaque(true);

		JMenu connectMenu = buildConnectMenu();// 菜单区域
		JMenu helpMenu = buildStyleMenu();

		MBar.add(connectMenu);
		MBar.add(helpMenu);
		setJMenuBar(MBar);

		JToolBar theBar = buildToolBar();// 工具栏区域
		pane.add(theBar, BorderLayout.NORTH);
		
		
	}

	public JMenu buildConnectMenu() {

		JMenu connectMenu = new JMenu("连接方式");
		connectMenu.setMnemonic('C');

		JRadioButtonMenuItem wifiConnect = new JRadioButtonMenuItem("WIFI",
				new ImageIcon("icons/new24.gif"));
		JRadioButtonMenuItem bluetoothConnect = new JRadioButtonMenuItem("蓝牙",
				new ImageIcon("icons/open24.gif"));
		JMenuItem exit = new JMenuItem("退出",
				new ImageIcon("icons/center24.gif"));

		wifiConnect.setMnemonic('W');

		bluetoothConnect.setMnemonic('B');

		wifiConnect.setAccelerator(KeyStroke.getKeyStroke('W',
				java.awt.Event.CTRL_MASK, false));
		bluetoothConnect.setAccelerator(KeyStroke.getKeyStroke('B',
				java.awt.Event.CTRL_MASK, false));

		wifiConnect.addActionListener(this);
		bluetoothConnect.addActionListener(this);
		exit.addActionListener(this);
		wifiConnect.setActionCommand(CMD_SELECT_WIFI_CONN);
		bluetoothConnect.setActionCommand(CMD_SELECT_BLUE_CONN);
		exit.setActionCommand(CMD_EXIT);

		connectMenu.add(wifiConnect);
		connectMenu.add(bluetoothConnect);
		connectMenu.addSeparator();
		connectMenu.add(exit);

		ButtonGroup bg = new ButtonGroup();
		bg.add(wifiConnect);
		bg.add(bluetoothConnect);

		return connectMenu;
	}// end of buildFileMenu()

	public JMenu buildStyleMenu() {

		JMenu helpMenu = new JMenu("帮助");
		helpMenu.setMnemonic('H');

		JMenuItem manual = new JMenuItem("手册",
				new ImageIcon("icons/left24.gif"));
		JMenuItem about = new JMenuItem("关于", new ImageIcon(
				"icons/center24.gif"));

		manual.setMnemonic('M');
		about.setMnemonic('A');

		manual.setAccelerator(KeyStroke.getKeyStroke('L',
				java.awt.Event.SHIFT_MASK, false));
		about.setAccelerator(KeyStroke.getKeyStroke('E',
				java.awt.Event.SHIFT_MASK, false));

		manual.addActionListener(this);
		about.addActionListener(this);
		manual.setActionCommand(CMD_SHOW_MANUAL);
		about.setActionCommand(CMD_SHOW_ABOUT);

		helpMenu.add(manual);
		helpMenu.add(about);

		return helpMenu;
	}// end of buildStyleMenu()

	public JToolBar buildToolBar() {

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);

		ToolBarAction btnWifiConn = new ToolBarAction("wifiConn",
				new ImageIcon("resources/icons/wifi_on.png"));
		ToolBarAction btnBlueConn = new ToolBarAction("blueConn",
				new ImageIcon("resources/icons/bluetooth_on.png"));
		
		selectWifiConn = toolBar.add(btnWifiConn);
		selectWifiConn.setActionCommand(CMD_SELECT_WIFI_CONN);
		selectWifiConn.addActionListener(this);
		selectBlueConn = toolBar.add(btnBlueConn);
		selectBlueConn.setActionCommand(CMD_SELECT_BLUE_CONN);
		selectBlueConn.addActionListener(this);

		toolBar.addSeparator();

		ToolBarAction btnManual = new ToolBarAction("manual", new ImageIcon(
				"resources/icons/help.png"));
		ToolBarAction btnAbout = new ToolBarAction("about", new ImageIcon(
				"resources/icons/about.png"));
		ToolBarAction btnExit = new ToolBarAction("exit", new ImageIcon(
				"resources/icons/exit.png"));
		JButton JB;
		JB = toolBar.add(btnManual);
		JB.setActionCommand(CMD_SHOW_MANUAL);
		JB.addActionListener(this);
		JB = toolBar.add(btnAbout);
		JB.setActionCommand(CMD_SHOW_ABOUT);
		JB.addActionListener(this);
		JB = toolBar.add(btnExit);
		JB.setActionCommand(CMD_EXIT);
		JB.addActionListener(this);

		toolBar.addSeparator();
		// JLabel JLfont = new JLabel("Font Type");
		// toolBar.add(JLfont);
		// toolBar.addSeparator();

		return toolBar;
	}// end of buildToolBar()
	
	

	public void actionPerformed(ActionEvent ae) {
		String cmdNow = ae.getActionCommand();
		if (CMD_SELECT_BLUE_CONN.equals(cmdNow)) {
			tabs.setEnabledAt(1, true);
			tabs.setEnabledAt(0, false);
			tabs.setSelectedComponent(blueTab);
			selectedType = CONN_TYPE_BLUE;
		} else if (CMD_SELECT_WIFI_CONN.equals(cmdNow)) {
			tabs.setEnabledAt(0, true);
			tabs.setEnabledAt(1, false);
			tabs.setSelectedComponent(wifiTab);
			selectedType = CONN_TYPE_WIFI;
		} else if (CMD_SHOW_MANUAL.equals(cmdNow)) {

		} else if (CMD_SHOW_ABOUT.equals(cmdNow)) {

		} else if (CMD_EXIT.equals(cmdNow)) {

		} else {

		}
	}
	
	public void addStatusBar() {
		int widths[] = {60,20};
		StatusBar statusBar = new StatusBar(widths,this);
		getContentPane().add(statusBar,BorderLayout.SOUTH);
	}

	public static void main(String[] args) {

		BlueHandle F = new BlueHandle();
		F.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		F.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});// end of addWindowListener
		F.addStatusBar();
		F.setVisible(true);
		double   width   =   Toolkit.getDefaultToolkit().getScreenSize().getWidth(); 
        double   height   =   Toolkit.getDefaultToolkit().getScreenSize().getHeight(); 
        F.setLocation(   (int)   (width   -   F.getWidth())   /   2, 
                                 (int)   (height   -   F.getHeight())   /   2); 
        F.setResizable(false);
        
        F.setGlassPane(new GlassPane());
	} // end of main

	class ToolBarAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		public ToolBarAction(String name, Icon icon) {
			super(name, icon);
		}

		public void actionPerformed(ActionEvent e) {

//			try {
//				theArea.append(e.getActionCommand() + "\n");
//			} catch (Exception ex) {
//			}
		}
	}// end of inner class ToolBarAction
}// end of class BlueHandle