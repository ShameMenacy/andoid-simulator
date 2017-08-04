/**
 *
 */
package ugame.vn.magazine.model;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import ugame.vn.magazine.event.ClientSocketHandler;
import ugame.vn.magazine.event.ConnectionErrorListener;
import ugame.vn.magazine.event.SMSEventListener;

/**
 * @author Shame
 * 
 */
public class SMSModel {

	private static final String IPSERVER = "192.168.1.108";
	private String phoneAddress;
	private String phoneNumber;
	private String smsBody;
	private List<SMSEventListener> eventListenerList;
	private ClientSocketHandler clientSocketHandler;
	private ConnectionErrorListener connectionError;

	public ClientSocketHandler getClientSocketHandler() {
		return clientSocketHandler;
	}

	public void setClientSocketHandler(ClientSocketHandler clientSocketHandler) {
		this.clientSocketHandler = clientSocketHandler;
	}

	public ConnectionErrorListener getConnectionError() {
		return connectionError;
	}

	public void setConnectionError(ConnectionErrorListener connectionError) {
		this.connectionError = connectionError;
	}

	public String getPhoneAddress() {
		return phoneAddress;
	}

	public void setPhoneAddress(String phoneAddress) {
		this.phoneAddress = phoneAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSmsBody() {
		return smsBody;
	}

	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
		fireSMSChangeEvent();
	}

	public SMSModel() {
		this.phoneAddress = "";
		this.phoneNumber = "";
		this.smsBody = "";
		this.eventListenerList = new ArrayList<SMSEventListener>();
	}

	public void connect() throws Exception {
		SocketAddress sockaddr = new InetSocketAddress(IPSERVER, 8000);
		// Create your socket
		Socket clientSocket = new Socket();
		// Connect with 10 s timeout
		clientSocket.connect(sockaddr, 3000);

		this.clientSocketHandler = new ClientSocketHandler(clientSocket);

		clientSocketHandler.setConnErrorListener(new ConnectionErrorListener() {
			@Override
			public void onConnectionError(Object obj) {
				connectionError.onConnectionError(obj);
			}
		});
	}

	public void retryConnect() {
		try {
			SocketAddress sockaddr = new InetSocketAddress(IPSERVER, 8000);
			// Create your socket
			Socket clientSocket = new Socket();
			// Connect with 10 s timeout
			clientSocket.connect(sockaddr, 4000);
			clientSocketHandler.setClientSocket(clientSocket);
		} catch (Exception ex) {
			Log.e("error", ex.getMessage());
			connectionError.onConnectionError(this);
		}
	}

	public boolean resetConnection() {
		try {
			SocketAddress sockaddr = new InetSocketAddress(IPSERVER, 8000);
			Socket clientSocket = new Socket();
			clientSocket.connect(sockaddr, 3000);
			this.clientSocketHandler = new ClientSocketHandler(clientSocket);
			clientSocketHandler
					.setConnErrorListener(new ConnectionErrorListener() {
						@Override
						public void onConnectionError(Object obj) {
							connectionError.onConnectionError(obj);
						}
					});
			return true;
		} catch (Exception ex) {
			Log.e("error", ex.getMessage());
			connectionError.onConnectionError(this);
		}
		return false;
	}

	public void addSMSChangeEventListener(SMSEventListener smsEventListener) {
		this.eventListenerList.add(smsEventListener);
	}

	private void fireSMSChangeEvent() {
		for (SMSEventListener smsEventListener : eventListenerList) {
			smsEventListener.onReceiptSMS(this);
		}

	}
}
