package my.sunny.communication.bean;

import java.io.IOException;

import javax.bluetooth.RemoteDevice;

public class DeviceDiscoveryRecord {

	private String deviceName = "";
	private RemoteDevice device;
	
	public DeviceDiscoveryRecord(RemoteDevice device) {
		this.device = device;
		try {
			this.deviceName = device.getFriendlyName(false);
            System.out.println("     name " + deviceName);
        } catch (IOException cantGetDeviceName) {
        }
	}

	public String getDeviceName() {
		return deviceName;
	}

	public RemoteDevice getDevice() {
		return device;
	}	
}
