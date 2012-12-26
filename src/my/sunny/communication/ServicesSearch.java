package my.sunny.communication;

import java.io.IOException;
//import java.util.Enumeration;
import java.util.Vector;
import javax.bluetooth.*;

import my.sunny.IBluetoothConst;

public class ServicesSearch implements IBluetoothConst {
	private final Vector<String> serviceFound = new Vector<String>();//服务列表
//	private 
	private RemoteDevice device;//要查找服务的设备

	public ServicesSearch(RemoteDevice device) {
		this.device = device;
	}

	public Vector<String> getServiceFound() throws InterruptedException {

		System.out.println("search return");
		return serviceFound;
	}

	public void search() throws IOException, InterruptedException {

		serviceFound.clear();

		UUID serviceUUID = new UUID(uuid, false);

		final Object serviceSearchCompletedEvent = new Object();

		DiscoveryListener listener = new DiscoveryListener() {

			public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
			}

			public void inquiryCompleted(int discType) {
			}

			public void servicesDiscovered(int transID,
					ServiceRecord[] servRecord) {
				for (int i = 0; i < servRecord.length; i++) {
					String url = servRecord[i].getConnectionURL(
							ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
					if (url == null) {
						continue;
					}
					serviceFound.add(url);
					DataElement serviceName = servRecord[i]
							.getAttributeValue(0x0100);
					if (serviceName != null) {
						System.out.println("service " + serviceName.getValue()
								+ " found " + url);
					} else {
						System.out.println("service found " + url);
					}
				}
			}

			public void serviceSearchCompleted(int transID, int respCode) {
				System.out.println("service search completed!");
				synchronized (serviceSearchCompletedEvent) {
					serviceSearchCompletedEvent.notifyAll();
				}
			}

		};

		UUID[] searchUuidSet = new UUID[] { serviceUUID };
		int[] attrIDs = new int[] { 
			0x0100 // Service name
		};

		synchronized (serviceSearchCompletedEvent) {
			String address = "";
			String friendlyName = "";
			try {
				address = device.getBluetoothAddress();
				friendlyName = device.getFriendlyName(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("search services(" + uuid + ") on " + address
					+ " " + friendlyName);
			//LocalDevice.getLocalDevice().getDiscoveryAgent();
			LocalDevice.getLocalDevice().getDiscoveryAgent().searchServices(
					attrIDs, searchUuidSet, device, listener);
			serviceSearchCompletedEvent.wait();
		}

	}

}