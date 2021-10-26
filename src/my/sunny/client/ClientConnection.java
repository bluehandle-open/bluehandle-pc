package my.sunny.client;

import java.io.IOException;

//import java.io.OutputStream;
import java.util.Vector;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import my.sunny.IBluetoothConst;
import my.sunny.communication.ServicesSearch;

/**
 * The Class ClientConnection.
 */
public class ClientConnection implements IBluetoothConst {
	
	/** The device. */
	private RemoteDevice device;
	
	/**
	 * Instantiates a new client connection.
	 * 
	 * @param device the device
	 */
	public ClientConnection(RemoteDevice device) {
		this.device = device;
	}

	/**
	 * Connect.
	 * 
	 * @return the stream connection
	 */
	public StreamConnection connect() {
		StreamConnection con = null;
		ServicesSearch search = new ServicesSearch(device);
		try {
			
			search.search();//查找服务
			Vector<String> serviceFound = search.getServiceFound();
			int len = serviceFound.size();
			if (len > 0) {//服务存在
				
				String url = serviceFound.get(len-1);
				con = (StreamConnection) Connector.open(url);//连接服务器
				if (con == null) {
					System.out.println("open " + url + "failed.");
				} else {
					System.out.println("open success.");
				}
			} else {
				System.out.println("none services was found.");
			}
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		return con;
	}	
	
}
