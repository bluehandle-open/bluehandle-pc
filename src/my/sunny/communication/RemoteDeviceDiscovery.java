package my.sunny.communication;

//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
import java.util.Vector;
import javax.bluetooth.*;
//import javax.swing.JLabel;
import javax.swing.JLabel;
//import javax.swing.SwingUtilities;

import my.sunny.communication.bean.DeviceDiscoveryRecord;

public class RemoteDeviceDiscovery {

    private static  final Vector<DeviceDiscoveryRecord> devicesDiscovered = new Vector<DeviceDiscoveryRecord>();
   
    public static Vector<DeviceDiscoveryRecord> getDevicesDiscovered() {
		return devicesDiscovered;
	}     
    
//    private static void setText(final JLabel messageArea,final String s) {
//
//    	messageArea.setText(s);
//	}
   
	public static void searchDevices(JLabel messageArea) {
		
        final Object inquiryCompletedEvent = new Object();//�����¼������

        devicesDiscovered.clear();//�����ʷ��¼
        
        
        DiscoveryListener listener = new DiscoveryListener() {//搜索监听器

            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                System.out.println("Device " + btDevice.getBluetoothAddress() + " found");
                devicesDiscovered.addElement(new DeviceDiscoveryRecord(btDevice));                
            }

            public void inquiryCompleted(int discType) {
                System.out.println("Device Inquiry completed!");
                
                synchronized(inquiryCompletedEvent){//发出通知
                    inquiryCompletedEvent.notifyAll();
                }
            }

            public void serviceSearchCompleted(int transID, int respCode) {
            }

            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            }
        };

        synchronized(inquiryCompletedEvent) {
            boolean started;
			try {
				started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);
				if (started) {
	                System.out.println("wait for device inquiry to complete...");
	                messageArea.setText("开始搜索......");
	                inquiryCompletedEvent.wait();//等待搜索完成
	                System.out.println(devicesDiscovered.size() +  " device(s) found");
	                messageArea.setText("搜索结束");
	            }
			} catch (BluetoothStateException e) {
				
				e.printStackTrace();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
            
        }
    }

}
