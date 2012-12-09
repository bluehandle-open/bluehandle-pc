package my.sunny.ui;

//import java.awt.Container;
//import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import my.sunny.client.ClientThread;
import my.sunny.communication.KeySetting;
import my.sunny.communication.message.AbstractSendMessage;
import my.sunny.communication.message.SendHandleSetting;
import my.sunny.communication.message.SendPlayerSetting;
import my.sunny.communication.message.SendPptSetting;

public class KeySettingUI implements ItemListener {
	private JPanel handlePane = new JPanel();
	private JPanel pptPane = new JPanel();
	private JPanel playerPane = new JPanel();
	private JFrame frame = new JFrame();
	private JRadioButton handleRadio = new JRadioButton("手柄");
    private JRadioButton pptRadio = new JRadioButton("ppt");
    private JRadioButton playerRadio = new JRadioButton("千千静听");
    
    private ClientThread clientInstance = new ClientThread();
	
	public KeySettingUI() {
		init();
	}
	
	private void init() {//��ʼ������
		frame.setLayout(null);
		
		JPanel top = new JPanel();//头部区域
		top.setBounds(10,0,380,60);
		top.setBorder((TitledBorder)BorderFactory.createTitledBorder("请选择连接方式"));
		
		handleRadio.addItemListener(this);
		pptRadio.addItemListener(this);
		playerRadio.addItemListener(this);
		ButtonGroup radios = new ButtonGroup();		
		
        radios.add(handleRadio);
        radios.add(pptRadio);
        radios.add(playerRadio);
        top.add(handleRadio);
        top.add(pptRadio);
        top.add(playerRadio);
		frame.add(top);//l�ӷ�ʽѡ������������
		
		JPanel finishArea = new JPanel();//��ɰ�ť����
		finishArea.setLayout(null);
		finishArea.setBounds(10, 310, 380, 50);
		JButton finishButton = new JButton("完成");
		finishButton.setBounds(310, 0, 60, 40);
		finishButton.addActionListener(new ActionListener() {//��ɰ�ť�ļ����¼�

			//@Override
			public void actionPerformed(ActionEvent e) {
				AbstractSendMessage message = null;
				if (handleRadio.isSelected()) {
					message = new SendHandleSetting();
				} else if (pptRadio.isSelected()) {
					message = new SendPlayerSetting();
				} else if (playerRadio.isSelected()) {
					message = new SendPptSetting();
				} else {
					JOptionPane.showMessageDialog(frame,"您尚未选择连接方式");
				}
				if (message != null) {
					try {
						clientInstance.send2Server(message);
						frame.setVisible(false);
					} catch (IOException e1) {						
						e1.printStackTrace();
					}
				}
			}
			
		});
		finishArea.add(finishButton);
		
		frame.add(finishArea);
		
		//����Ϊ������������
		addHandleSetting();
		addPptPane();
		addPlayerPane();
		
		frame.setSize(400, 400);		
		frame.setVisible(true);
	}
	
	private JButton getKeyButton(String initText, Rectangle r, int i) {//��ɰ���ť
		final JButton btn = new JButton(initText);
		
		btn.setBorder((BevelBorder)BorderFactory.createLoweredBevelBorder());
		btn.setBounds(r);
		
		btn.setHorizontalAlignment(JButton.CENTER );
		btn.setFocusable(true);
		btn.addKeyListener(new HandleKeyAdapter(btn,i));
		return btn;
	}
	
	class HandleKeyAdapter extends KeyAdapter {
		private JButton btn;
		private int index;
		public HandleKeyAdapter(JButton btn, int index) {
			this.btn = btn;
			this.index = index;
		}
		
		public void keyPressed(KeyEvent e) {
			btn.setText(String.valueOf(e.getKeyChar()));
			KeySetting.handleKeys[index] = (byte)e.getKeyCode();
		}
	}
	
	private JPanel getKeyGroupPanel(Rectangle r) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(r);
		panel.setBorder((BevelBorder)BorderFactory.createLoweredBevelBorder());
		
		return panel;
	}

	public  void addHandleSetting() {//����ֱ�����
		
		handlePane.setBounds(0, 80, 400, 400);//�ֱ���������
		handlePane.setLayout(null);			
		
		JPanel middleContent = new JPanel();
		middleContent.setBounds(10, 0, 380, 150);
		middleContent.setLayout(null);		
				
		JPanel leftMiddle = getKeyGroupPanel(new Rectangle(0, 0, 150, 150));//左侧区域������Ƽ�����		
		
		JButton up = getKeyButton("w",new Rectangle(50,0,50,50),0);//UP	
		leftMiddle.add(up);
		
		JButton left = getKeyButton("a",new Rectangle(0,50,50,50),1);//LEFT
		leftMiddle.add(left);
		
		JButton right = getKeyButton("d",new Rectangle(100,50,50,50),3);//right
		leftMiddle.add(right);

		JButton down = getKeyButton("s",new Rectangle(50,100,50,50),2);//down
		leftMiddle.add(down);
		
		middleContent.add(leftMiddle);		
		
		JPanel rightMiddle = getKeyGroupPanel(new Rectangle(220, 0, 150, 150));//右侧区域���ܼ�
				
		JButton selectBoom = getKeyButton("u",new Rectangle(0,25,50,50),4);//选择武器������
		rightMiddle.add(selectBoom);
		
		JButton dropBoom = getKeyButton("i",new Rectangle(50,25,50,50),5);//投掷武器Ͷ������
		rightMiddle.add(dropBoom);
		
		JButton other1 = getKeyButton("o",new Rectangle(100,25,50,50),6);//其他功能键1������
		rightMiddle.add(other1);
		
		JButton fire = getKeyButton("j",new Rectangle(0,75,50,50),7);//开火����
		rightMiddle.add(fire);
		
		JButton jump = getKeyButton("k",new Rectangle(50,75,50,50),8);//跳��
		rightMiddle.add(jump);
		
		JButton other2 = getKeyButton("l",new Rectangle(100,75,50,50),9);//其他功能键2������
		rightMiddle.add(other2);
		
		middleContent.add(rightMiddle);
		
		JPanel bottomContent = getKeyGroupPanel(new Rectangle(10, 170, 380, 100));//Ͷ底部区域
		
		JButton select = getKeyButton("1",new Rectangle(130,0,50,50),10);//select
		bottomContent.add(select);
		JButton start = getKeyButton("5",new Rectangle(200,0,50,50),11);//start
		bottomContent.add(start);
		
		handlePane.add(middleContent);		
		handlePane.add(bottomContent);		
		
		frame.add(handlePane);		
		frame.repaint();		
	}
	
	public void addPptPane() {//���ppt����
		
		pptPane.setBounds(0, 80, 400, 400);//���������仰������ʾ
		JLabel pptInfo = new JLabel();
		String info = "ppt按键";
		pptInfo.setText(info);
		pptPane.add(pptInfo);
		
		pptPane.setVisible(false);
		frame.add(pptPane);
		frame.repaint();
	}
	
	public void addPlayerPane() {//���ǧǧ��������
		
		playerPane.setBounds(0, 80, 400, 400);
		JLabel playerInfo = new JLabel();
		String infoPlayer = "����千千静听按键";
		playerInfo.setText(infoPlayer);
		playerPane.add(playerInfo);
		
		playerPane.setVisible(false);
		frame.add(playerPane);
		frame.repaint();
	}
	
	//@Override
	public void itemStateChanged(ItemEvent e) {//radio切换����
		
		if (e.getSource() == handleRadio) {
			handlePane.setVisible(true);
			pptPane.setVisible(false);
			playerPane.setVisible(false);
		} else if (e.getSource() == pptRadio) {
			handlePane.setVisible(false);
			pptPane.setVisible(true);
			playerPane.setVisible(false);
		} else if (e.getSource() == playerRadio) {
			handlePane.setVisible(false);
			pptPane.setVisible(false);
			playerPane.setVisible(true);
		}
	}
}
