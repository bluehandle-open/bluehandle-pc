package my.sunny.ui;

//import java.io.IOException;
import java.util.Vector;

//import javax.bluetooth.RemoteDevice;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import my.sunny.communication.bean.DeviceDiscoveryRecord;

public class RemoteDeviceTableMode  implements TableModel {
	
	private Vector<DeviceDiscoveryRecord> devicesDiscovered;
	
	public RemoteDeviceTableMode(Vector<DeviceDiscoveryRecord> devicesDiscovered) {
		this.devicesDiscovered = devicesDiscovered;
	}

	//@Override
	public void addTableModelListener(TableModelListener l) {
		
	}

	//@Override
	public Class<?> getColumnClass(int columnIndex) {		
		return String.class;
	}

	//@Override
	public int getColumnCount() {		
		return 3;
	}

	//@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) {
			return "index";
		} else if (columnIndex == 1) {
			return "mac";
		} else {
			return "name";
		}		
	}

	//@Override
	public int getRowCount() {		
		return devicesDiscovered.size();
	}

	//@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if (columnIndex == 0) {
			return rowIndex + 1;
		} else if (columnIndex == 1) {
			return devicesDiscovered.get(rowIndex).getDevice().getBluetoothAddress();
		} else {
//			System.out.println("show bluetooth name of "+rowIndex);
			return devicesDiscovered.get(rowIndex).getDeviceName();	
		}
		
	}

	//@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	//@Override
	public void removeTableModelListener(TableModelListener l) {
		
	}

	//@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		
	}

}
