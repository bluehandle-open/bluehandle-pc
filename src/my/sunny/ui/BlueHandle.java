package my.sunny.ui;

import static com.whyun.util.ResourceUtil.getImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.plaf.FontUIResource;

import my.sunny.util.CMDUtil;

import com.whyun.util.FileFindUtil;

public class BlueHandle extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6761264863876829858L;

	public static final int CONN_TYPE_BLUE = 1;
	public static final int CONN_TYPE_WIFI = 0;
	public static final int CONN_NONE = -1;

	private static final int DEFAULT_WIDTH = 620;
	private static final int DEFAULT_HEIGHT = 520;
	private int connectedType = CONN_NONE;

	private static final String CMD_SELECT_BLUE_CONN = "selectBlueConnect";
	private static final String CMD_SELECT_WIFI_CONN = "selectWifiConnect";
	public static final int MSG_TYPE_NOMARL = 1;
	public static final int MSG_TYPE_ERROR = 2;
	public static final int MSG_TYPE_SUCCESS = 3;
	private static final String CMD_SHOW_MANUAL = "showManual";
	private static final String CMD_SHOW_ABOUT = "showAbout";
	private static final String CMD_EXIT = "exit";
	private static final String CMD_SHOW_CONN_TYPE = "showConnectType";

	private JButton selectWifiConn;
	private JButton selectBlueConn;

	private AbstractTabPanel wifiTab;
	private AbstractTabPanel blueTab;

	private JTabbedPane tabs;

	public static final String CONNECTED_STATUS = "已连接";
	public static final String NOT_CONNECTED_STATUS = "尚未连接";

	private static final String MANAUL_FILE_NAME = "蓝色手柄使用手册.doc";

	/** 消息显示区 */
	private JTextField message = new JTextField("");

	/** 状态栏 */
	private StatusBar statusBar;

	/** 关于窗口 */
	private AboutFrame aboutFrame;

	/** wifi连接菜单 */
	private JRadioButtonMenuItem wifiConnect;

	/** 蓝牙连接菜单 */
	private JRadioButtonMenuItem bluetoothConnect;

	public BlueHandle() {

		super("蓝色手柄");
		addComponentsToPane(getContentPane());
	}

	private void addComponentsToPane(Container pane) {
		tabs = new JTabbedPane();// tab区

		int realWidth = DEFAULT_WIDTH - 10;
		int realHeight = DEFAULT_HEIGHT - 50;

		wifiTab = new WifiClientPanel(realWidth, realHeight, this);//

		tabs.addTab("wifi连接", wifiTab);

		blueTab = new BluetoothClientPanel(realWidth, realHeight, this);//

		tabs.add("蓝牙连接", blueTab);
		tabs.setEnabledAt(0, false);
		tabs.setEnabledAt(1, false);
		pane.add(tabs);
		wifiTab.disableUI();

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
		connectMenu.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e) {
				// System.out.println("menuCanceled");
			}

			public void menuDeselected(MenuEvent e) {
				// System.out.println("menuDeselected");
			}

			public void menuSelected(MenuEvent e) {
				if (connectedType == CONN_NONE) {
					wifiConnect.setSelected(false);
					bluetoothConnect.setSelected(false);
				} else if (connectedType == CONN_TYPE_BLUE) {
					wifiConnect.setSelected(false);
					bluetoothConnect.setSelected(true);
				} else if (connectedType == CONN_TYPE_WIFI) {
					wifiConnect.setSelected(true);
					bluetoothConnect.setSelected(false);
				}
			}
		});

		wifiConnect = new JRadioButtonMenuItem("WIFI", new ImageIcon(
				"icons/new24.gif"));
		bluetoothConnect = new JRadioButtonMenuItem("蓝牙", new ImageIcon(
				"icons/open24.gif"));
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

		System.out.println(toolBar.getLayout());
		toolBar.setFloatable(true);

		ToolBarAction btnWifiConn = new ToolBarAction("建立wifi连接",
				getImageIcon("/icons/wifi_on.png"));
		ToolBarAction btnBlueConn = new ToolBarAction("建立蓝牙连接",
				getImageIcon("/icons/bluetooth_on.png"));

		selectWifiConn = toolBar.add(btnWifiConn);
		selectWifiConn.setActionCommand(CMD_SELECT_WIFI_CONN);
		selectWifiConn.addActionListener(this);
		selectWifiConn.setToolTipText((String) btnWifiConn
				.getValue(Action.NAME));

		selectBlueConn = toolBar.add(btnBlueConn);
		selectBlueConn.setActionCommand(CMD_SELECT_BLUE_CONN);
		selectBlueConn.addActionListener(this);
		selectBlueConn.setToolTipText((String) btnBlueConn
				.getValue(Action.NAME));

		toolBar.addSeparator();

		ToolBarAction btnManual = new ToolBarAction("手册",
				getImageIcon("/icons/help.png"));
		ToolBarAction btnAbout = new ToolBarAction("关于",
				getImageIcon("/icons/about.png"));
		ToolBarAction btnExit = new ToolBarAction("退出",
				getImageIcon("/icons/exit.png"));
		JButton JB;
		JB = toolBar.add(btnManual);
		JB.setActionCommand(CMD_SHOW_MANUAL);
		JB.addActionListener(this);
		JB.setToolTipText((String) btnManual.getValue(Action.NAME));

		JB = toolBar.add(btnAbout);
		JB.setActionCommand(CMD_SHOW_ABOUT);
		JB.addActionListener(this);
		JB.setToolTipText((String) btnAbout.getValue(Action.NAME));

		JB = toolBar.add(btnExit);
		JB.setActionCommand(CMD_EXIT);
		JB.addActionListener(this);
		JB.setToolTipText((String) btnExit.getValue(Action.NAME));

		toolBar.addSeparator();

		message.setBorder(new EtchedBorder());
		message.setEditable(false);

		message.setOpaque(false);
		message.setPreferredSize(new Dimension(200, 30));
		Font font = new Font("宋体", Font.PLAIN, 14);
		message.setFont(font);
		toolBar.add(message);
		toolBar.addSeparator();

		return toolBar;
	}// end of buildToolBar()

	public void showMessage(int type, String msg) {
		switch (type) {
		case MSG_TYPE_NOMARL:
			message.setForeground(Color.BLACK);
			break;
		case MSG_TYPE_ERROR:
			message.setForeground(Color.RED);
			break;
		case MSG_TYPE_SUCCESS:
			message.setForeground(Color.BLUE);
			break;
		}
		message.setText("  " + msg);
	}

	public void showInfo(String msg) {
		showMessage(MSG_TYPE_NOMARL, msg);
	}

	public void showError(String msg) {
		showMessage(MSG_TYPE_ERROR, msg);
		this.connectedType = CONN_NONE;
	}

	public void showSuccess(String msg) {
		showMessage(MSG_TYPE_SUCCESS, msg);
	}

	private void showConnTypeStatus(String type) {
		statusBar.setStatus(0, "当前连接方式：" + type);
	}

	public void showConnStatus(String status) {
		statusBar.setStatus(1, "当前连接状态：" + status);
	}

	public void showConnectedStatus() {
		showConnStatus(CONNECTED_STATUS);
	}

	public void showNotConnectedStatus() {
		showConnStatus(NOT_CONNECTED_STATUS);
	}

	public void setConnectedType(int connectedType) {
		this.connectedType = connectedType;
	}

	public void actionPerformed(ActionEvent ae) {
		String cmdNow = ae.getActionCommand();
		System.out.println(cmdNow);
		if (CMD_SELECT_BLUE_CONN.equals(cmdNow)) {
			if (connectedType == CONN_TYPE_WIFI) {
				int ifadd = JOptionPane.showConfirmDialog(this,
						"当前wifi方式已连接，选择蓝牙连接将会断开之前的连接，是否继续！", "警告",
						JOptionPane.YES_NO_OPTION);
				if (ifadd == JOptionPane.NO_OPTION) {
					return;
				} else {
					wifiTab.close();
				}
			}
			//
			tabs.setEnabledAt(1, true);
			tabs.setEnabledAt(0, false);
			tabs.setSelectedComponent(blueTab);

			showInfo("");
			showConnTypeStatus("蓝牙");
		} else if (CMD_SELECT_WIFI_CONN.equals(cmdNow)) {
			if (connectedType == CONN_TYPE_BLUE) {
				int ifadd = JOptionPane.showConfirmDialog(this,
						"当前蓝牙方式已连接，选择wifi连接将会断开之前的连接，是否继续！", "警告",
						JOptionPane.YES_NO_OPTION);
				if (ifadd == JOptionPane.NO_OPTION) {
					return;
				} else {
					blueTab.close();
				}
			}
			tabs.setEnabledAt(0, true);
			tabs.setEnabledAt(1, false);
			tabs.setSelectedComponent(wifiTab);
			wifiTab.enableUI();

			showInfo("");
			showConnTypeStatus("wifi");
		} else if (CMD_SHOW_MANUAL.equals(cmdNow)) {
			String filePath = FileFindUtil.find(MANAUL_FILE_NAME);
			if (filePath != null) {
				try {
					CMDUtil.executeFile(filePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (CMD_SHOW_ABOUT.equals(cmdNow)) {
			if (aboutFrame == null) {
				aboutFrame = new AboutFrame();
				aboutFrame.start();
			} else {
				aboutFrame.reShow();
			}

		} else if (CMD_EXIT.equals(cmdNow)) {
			exitShow();
		} else if (CMD_SHOW_CONN_TYPE.equals(cmdNow)) {

		} else {

		}
	}

	private void exitShow() {
		String msg = "是否退出？";
		if (connectedType != CONN_NONE) {
			msg = "尚有连接建立，" + msg;
		}
		int ifadd = JOptionPane.showConfirmDialog(this, msg, "警告",
				JOptionPane.YES_NO_OPTION);
		if (ifadd == JOptionPane.NO_OPTION) {
			return;
		} else {
			blueTab.close();
			wifiTab.close();
			System.exit(0);
		}
	}

	public void addStatusBar() {
		int widths[] = { 60, 20 };
		String texts[] = { "当前连接方式：尚未选择", "当前连接状态：尚未连接" };
		statusBar = new StatusBar(widths, texts, this);
		getContentPane().add(statusBar, BorderLayout.SOUTH);
	}

	public void start() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exitShow();
			}
		});
		addStatusBar();
		setVisible(true);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setLocation((int) (width - getWidth()) / 2,
				(int) (height - getHeight()) / 2);
		setResizable(false);

		showError("请选择连接方式");
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

	public static void main(String[] args) {
		InitGlobalFont(new Font("宋体", Font.PLAIN, 13));
		BlueHandle F = new BlueHandle();
		F.start();
	} // end of main

	class ToolBarAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		public ToolBarAction(String name, Icon icon) {
			super(name, icon);
		}

		public void actionPerformed(ActionEvent e) {

			// try {
			// theArea.append(e.getActionCommand() + "\n");
			// } catch (Exception ex) {
			// }
		}
	}// end of inner class ToolBarAction
}// end of class BlueHandle