package my.sunny.ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import my.sunny.client.ClientConnection;
import my.sunny.client.ClientThread;
import my.sunny.communication.RemoteDeviceDiscovery;
import my.sunny.communication.bean.DeviceDiscoveryRecord;

public class BluetoothClientPanel extends AbstractTabPanel {
	private static final long serialVersionUID = -6484707282790803696L;
	private JTable tableBlueTooths;
	private JButton buSearch;
	private JLabel messageArea;
	private JPanel container;//
	private JScrollPane scrollPane = null;
	
	private Vector<DeviceDiscoveryRecord> devicesDiscovered;
	private ClientThread lastClientInstance;
	
	public BluetoothClientPanel(int width, int height, BlueHandle parentFrame) {
		super(width, height, parentFrame);
	}

	@Override
	protected void init() {
		container  = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		JPanel searchArea = new JPanel();
		messageArea = new JLabel("请选择一个设备进行连接");
		messageArea.setMinimumSize(new Dimension(50,10));
		searchArea.add(messageArea);
		
		buSearch = new JButton("搜索");
		searchArea.add(buSearch);
		container.add(searchArea);
		add(container,BorderLayout.PAGE_START);
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
				parentFrame.showNotConnectedStatus();
				parentFrame.showInfo("");
				
				RemoteDevice device = devicesDiscovered.get(selectIndex).getDevice();
				//System.out.println(device.getBluetoothAddress());
				ClientConnection connection = new ClientConnection(device);//��ʼ�����캯��
				StreamConnection socket = connection.connect();//l�ӷ���
				ClientThread clientInstance = new ClientThread();
				
				if (socket != null) {
					try {
						clientInstance.initThread(socket,parentFrame);
						if (clientInstance.isHasInit()) {//建立连接成功
							//new KeySettingUI();
							parentFrame.showSuccess("初始化成功");
							parentFrame.showConnectedStatus();
							parentFrame.setConnectedType(BlueHandle.CONN_TYPE_BLUE);
							lastClientInstance = clientInstance;
						} else {
							parentFrame.showError("建立连接失败");
						}
					} catch (IOException e1) {//����������쳣	
						e1.printStackTrace();
						parentFrame.showError("初始化失败，错误码1");
					} catch (AWTException e1) {//���ܻ�ȡ����robot		
						e1.printStackTrace();
						parentFrame.showError("初始化失败，错误码2");
					}
				} else {
					parentFrame.showError("设备初始化失败");					
				}
				
			}
		}
	}

	@Override
	protected void addEventListener() {
		buSearch.addActionListener(new ActionListener() {

			//@Override
			public synchronized void actionPerformed(ActionEvent e) {				
				//searchDeviceList();
				buSearch.setEnabled(false);
				new TableSearchWorker().execute();
			}
			
		});
	}

	@Override
	protected void close() {
		if (lastClientInstance != null) {
			lastClientInstance.stopServer();
		}
		
	}
	
	class TableSearchWorker  extends SwingWorker<RemoteDeviceTableMode, String> {
		
		public TableSearchWorker() {
			
		}

		@Override
		protected RemoteDeviceTableMode doInBackground() throws Exception {
			publish("正在搜索……");
			RemoteDeviceDiscovery.searchDevices();
			devicesDiscovered = RemoteDeviceDiscovery.getDevicesDiscovered();
			RemoteDeviceTableMode tableModel = new RemoteDeviceTableMode(devicesDiscovered);
			return tableModel;
		}

		@Override
		protected void process(List<String> chunks) {
			String msg = chunks.get(chunks.size()-1);
			parentFrame.showInfo(msg);
		}
		
		private void refreshTable() {
			try {
				tableBlueTooths = new JTable();
				RemoteDeviceTableMode tableModel = get();
				
				tableBlueTooths.setModel(tableModel);
				
				JPopupMenu pop = getTablePop();
				tableBlueTooths.setComponentPopupMenu(pop);

				scrollPane = new JScrollPane(tableBlueTooths);	//
				tableBlueTooths.setPreferredScrollableViewportSize(new Dimension(400,100));
				scrollPane.setAutoscrolls(true);
				scrollPane.setVisible(true);
				container.add(scrollPane);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
		}

		@Override
		protected void done() {
			if (scrollPane == null) {//第一次搜索
				refreshTable();
				SwingUtilities.updateComponentTreeUI(parentFrame);
				System.out.println("第一次刷新列表");
			} else {//刷新
				container.remove(scrollPane);
				SwingUtilities.updateComponentTreeUI(parentFrame);
				refreshTable();	
				System.out.println("重新刷新列表");
			}
			parentFrame.showInfo("");
			buSearch.setEnabled(true);
		}
		
	}

}
