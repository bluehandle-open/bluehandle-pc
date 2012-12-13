package my.sunny.ui;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import my.sunny.client.ClientConnection;
import my.sunny.client.ClientThread;
import my.sunny.communication.RemoteDeviceDiscovery;
import my.sunny.communication.bean.DeviceDiscoveryRecord;

public class BluetoothClientPanel extends AbstractTabPanel {
	private static final long serialVersionUID = -6484707282790803696L;
	private JTable tableBlueTooths;
	private JButton buSearch;
	private JLabel messageArea;
	private JScrollPane scrollPane = null;
	
	private Vector<DeviceDiscoveryRecord> devicesDiscovered;
	private ClientThread lastClientInstance;
	
	public BluetoothClientPanel(int width, int height, JFrame parentFrame) {
		super(width, height, parentFrame);
	}

	@Override
	protected void init() {
		this.setLayout(new FlowLayout());
		messageArea = new JLabel("请选择一个设备进行连接");
		messageArea.setMinimumSize(new Dimension(50,10));
		add(messageArea);
		
		buSearch = new JButton("搜索");
		add(buSearch);
		
	}
	
	private void refreshTable() {
		tableBlueTooths = new JTable();//�������
		RemoteDeviceDiscovery.searchDevices(messageArea);
		devicesDiscovered = RemoteDeviceDiscovery.getDevicesDiscovered();
		RemoteDeviceTableMode tableModel = new RemoteDeviceTableMode(devicesDiscovered);
		tableBlueTooths.setModel(tableModel);
		
		JPopupMenu pop = getTablePop();
		tableBlueTooths.setComponentPopupMenu(pop);

		scrollPane = new JScrollPane(tableBlueTooths);	//������������������	
		tableBlueTooths.setPreferredScrollableViewportSize(new Dimension(400,100));
		scrollPane.setAutoscrolls(true);		
		add(scrollPane);
	}
	
	private void searchDeviceList() {
		if (scrollPane == null) {//第一次搜索��didiyici sousu diyic 
			refreshTable();
			System.out.println("第一次刷新列表");
		} else {//�����刷新�б�
			remove(scrollPane);
			SwingUtilities.updateComponentTreeUI(parentFrame);
			refreshTable();	
			System.out.println("重新刷新列表");
		}		
	}
	
	private JPopupMenu getTablePop() {
		JPopupMenu pop = new JPopupMenu();
		JMenuItem connectItem = new JMenuItem("连接");
		connectItem.setActionCommand("connect");
		JMenuItem deleteItem = new JMenuItem("删除");
		deleteItem.setActionCommand("remove");
		
		ActionListener al = new ActionListener() {//����˵������¼�

			//@Override
			public void actionPerformed(ActionEvent e) {
				String s = e.getActionCommand();
				popMenuAction(s);
			}
			
		};
		
		connectItem.addActionListener(al);
		deleteItem.addActionListener(al);
		
		pop.add(connectItem);
		pop.add(deleteItem);
		
		return pop;
	}
	
	private void popMenuAction(String command) {
		//�õ��ڱ���ϵ�ѡ�е���
		final int selectIndex = tableBlueTooths.getSelectedRow();
		if (selectIndex == -1) {
			JOptionPane.showMessageDialog(parentFrame,"请选择一个蓝牙设备");  
			
		} else {
			if ("remove".equals(command)) {//删除
				devicesDiscovered.remove(selectIndex);
				tableBlueTooths.removeRowSelectionInterval(selectIndex, selectIndex);
				SwingUtilities.updateComponentTreeUI(tableBlueTooths);
			} else if ("connect".equals(command)) {//连接
				if (lastClientInstance != null) {
					lastClientInstance.stopServer();
				}
				RemoteDevice device = devicesDiscovered.get(selectIndex).getDevice();
				//System.out.println(device.getBluetoothAddress());
				ClientConnection connection = new ClientConnection(device);//��ʼ�����캯��
				StreamConnection socket = connection.connect();//l�ӷ���
				ClientThread clientInstance = new ClientThread();
				
				if (socket != null) {
					try {
						clientInstance.initThread(socket);
						if (clientInstance.isHasInit()) {//建立连接成功
							//new KeySettingUI();
							JOptionPane.showMessageDialog(parentFrame,"建立连接成功");
							lastClientInstance = clientInstance;
						} else {
							JOptionPane.showMessageDialog(parentFrame,"建立连接失败");
						}
					} catch (IOException e1) {//����������쳣	
						e1.printStackTrace();
					} catch (AWTException e1) {//���ܻ�ȡ����robot		
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(parentFrame,"设备初始化失败");					
				}
				
			}
		}
	}

	@Override
	protected void addEventListener() {
		buSearch.addActionListener(new ActionListener() {

			//@Override
			public void actionPerformed(ActionEvent e) {				
				searchDeviceList();	
			}
			
		});
	}

	@Override
	protected void close() {
		if (lastClientInstance != null) {
			lastClientInstance.stopServer();
		}
		
	}

}
