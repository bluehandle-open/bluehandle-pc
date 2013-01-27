package my.sunny.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import my.sunny.ui.component.ImagePanel;

import com.whyun.util.JarUtil;

public class AboutFrame {

	/**
	 * 
	 */
	private JFrame frame = new JFrame("关于");
	private JButton ok;
	private boolean hasInit = false;
	
	private void addComponentsToPane(Container pane) {
		JPanel container = new JPanel();//
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBorder(new TitledBorder(""));
		infoPanel.setPreferredSize(new Dimension(400,100));
		

		//                                 
		ImagePanel image = new ImagePanel("/icons/bluehandle.png");
//		image.setPreferredSize(new Dimension(100,75));
		image.setBounds(10, 5, 80, 75);
		infoPanel.add(image);
		
		JarUtil jar = new JarUtil(AboutFrame.class);
		String version = jar.getVersion();
		JLabel desp = new JLabel("<html>" +
				"支持用手机遥控电脑，支持以wifi或者蓝牙方式连接电脑。" +
				"连接成功后，可以控制游戏机手柄、千千静听或者PPT，更可以" +
				"添加任意自定义快捷键，让你想遥控啥就遥控啥。<br />" +
				"版本："+version+"</html>");
		desp.setBounds(100,5,290,85);
		infoPanel.add(desp);
		
		container.add(infoPanel);
		
		JTextArea change = new JTextArea();
		JScrollPane scroll = new JScrollPane(change);
		//分别设置水平和垂直滚动条自动出现 
		scroll.setHorizontalScrollBarPolicy( 
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		scroll.setVerticalScrollBarPolicy( 
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		change.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		change.setOpaque(false);
		change.setEditable(false);			
				
		String content = JarUtil.getJarResourceContent("/changelog.txt");
		change.setText(new String(content));			
		
		scroll.setPreferredSize(new Dimension(360,120));
		
		container.add(scroll);
		
		pane.add(container,BorderLayout.PAGE_START);
		
		JPanel buttonArea = new JPanel();

		ok = new JButton("确定");
		ok.setBounds(350, 10, 40, 20);
		buttonArea.add(ok);
		
		pane.add(buttonArea,BorderLayout.LINE_END);
	}
	public void start() {
		// Set up the content pane.
		addComponentsToPane(frame.getContentPane());
		// Display the window.
		frame.pack();
		frame.setSize(402, 302);
		double   width   =   Toolkit.getDefaultToolkit().getScreenSize().getWidth(); 
        double   height   =   Toolkit.getDefaultToolkit().getScreenSize().getHeight(); 
        frame.setLocation(   (int)   (width   -   frame.getWidth())   /   2, 
                                 (int)   (height   -   frame.getHeight())   /   2); 

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		addEvent();
		hasInit = true;
	}
	
	private void addEvent() {
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);				
			}
			
		});
		
	}
	
	public boolean reShow() {
		if (hasInit) {
			frame.setVisible(true);
		}
		return hasInit;
	}
	
	public static void main(String argv[]) {
		new AboutFrame().start();
	}
}
