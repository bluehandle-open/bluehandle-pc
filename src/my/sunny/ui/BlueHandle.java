package my.sunny.ui;

import java.awt.BorderLayout;
import java.awt.Container;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

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

	public BlueHandle() {

		super("蓝色手柄");
//		theArea = new JTextArea();
//		theArea.setEditable(false);
//		getContentPane().add(new JScrollPane(theArea));
		addComponentsToPane(getContentPane());
		JMenuBar MBar = new JMenuBar();
		MBar.setOpaque(true);

		JMenu connectMenu = buildConnectMenu();//菜单区域
		JMenu helpMenu = buildStyleMenu();

		MBar.add(connectMenu);
		MBar.add(helpMenu);
		setJMenuBar(MBar);
		
		JToolBar theBar = buildToolBar();//工具栏区域
	    this.getContentPane().add(theBar,BorderLayout.NORTH);
	}// end of BlueHandle()
	
	private void addComponentsToPane(Container pane) {
		JTabbedPane tabbedPane = new JTabbedPane();
		
		int realWidth = DEFAULT_WIDTH -10;
		int realHeight = DEFAULT_HEIGHT - 50;		
		
		JPanel container = new WifiClientPanel(realWidth,realHeight,this);//
		
		tabbedPane.addTab("wifi连接", container);
		
		JPanel container1 = new BluetoothClientPanel(realWidth,realHeight,this);//
		
		tabbedPane.add("蓝牙连接",container1);
		
		pane.add(tabbedPane);
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

		bluetoothConnect.addActionListener(this);

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

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
		manual.setActionCommand("");
		about.setMnemonic('A');

		manual.setAccelerator(KeyStroke.getKeyStroke('L',
				java.awt.Event.SHIFT_MASK, false));
		about.setAccelerator(KeyStroke.getKeyStroke('E',
				java.awt.Event.SHIFT_MASK, false));

		manual.addActionListener(this);
		about.addActionListener(this);

		helpMenu.add(manual);
		helpMenu.add(about);

		return helpMenu;
	}// end of buildStyleMenu()

	public void actionPerformed(ActionEvent ae) {
		try {
			theArea.append("* action '" + ae.getActionCommand()
					+ "' performed. *\n");
		} catch (Exception e) {
			System.out.println("actionPerformed Exception:" + e);
		}
	}
	
	public JToolBar buildToolBar() {
		  
		  JToolBar toolBar = new JToolBar();
	      toolBar.setFloatable(true);
	    
		  ToolBarAction tba_new   = new ToolBarAction("wifiConn",new ImageIcon("resources/icons/wifi_on.png"));
		  ToolBarAction tba_open  = new ToolBarAction("blueConn",new ImageIcon("resources/icons/bluetooth_on.png"));
		  
	    
		  JButton JB;
		  JB = toolBar.add(tba_new);
		  JB.setActionCommand("#TooBar_NEW performed!");
		  JB = toolBar.add(tba_open);
		  JB.setActionCommand("#ToolBar_OPEN performed!");		  
	    
		  toolBar.addSeparator();
	        
		  ToolBarAction tba_B  = new ToolBarAction("manual",new ImageIcon("resources/icons/help.png"));
		  ToolBarAction tba_I  = new ToolBarAction("about",new ImageIcon("resources/icons/about.png"));
		  ToolBarAction tba_U  = new ToolBarAction("exit",new ImageIcon("resources/icons/exit.png")); 
		  JB = toolBar.add(tba_B);
		  JB.setActionCommand("#ToolBar_Bold performed!");
		  JB = toolBar.add(tba_I);
		  JB.setActionCommand("#ToolBar_Italic performed!");
		  JB = toolBar.add(tba_U);
		  JB.setActionCommand("#ToolBar_Underline performed!");
	  
		  toolBar.addSeparator();    
//		  JLabel JLfont = new JLabel("Font Type");
//		  toolBar.add(JLfont);
//		  toolBar.addSeparator();		  
		
		  return toolBar;
		}//end of buildToolBar()

	public static void main(String[] args) {

		JFrame F = new BlueHandle();
		F.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		F.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});// end of addWindowListener
		F.setVisible(true);
	} // end of main
	
	class ToolBarAction extends AbstractAction{

	      public ToolBarAction(String name,Icon icon){
	        super(name,icon);
	      }

	      public void actionPerformed(ActionEvent e){
	    
	        try{
	          theArea.append(e.getActionCommand()+"\n");
	        }catch(Exception ex){}
	      }
	    }//end of inner class ToolBarAction
}// end of class BlueHandle