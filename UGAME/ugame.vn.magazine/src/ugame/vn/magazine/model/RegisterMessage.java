/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ugame.vn.magazine.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import ugame.vn.magazine.event.ClientSocketHandler;
import android.util.Log;

/**
 * 
 * @author big
 */
public class RegisterMessage extends Message {
	private String phoneNumber;
	private ClientSocketHandler clientHander;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public RegisterMessage(ClientSocketHandler clientHandler) throws Exception {
		this.clientHander = clientHandler;
		init();
	}

	private void init() throws Exception {
		setMsgType(MessageType.ERROR_MESSAGE);
		Socket socket = clientHander.getClientSocket();
		setDataInputStream(socket.getInputStream());
		setDataOutPutStream(socket.getOutputStream());
	}

	@Override
	public void readMessage()throws Exception {
		DataInputStream dataInput = getDataInputStream();		
			int msgLength = dataInput.readInt();
			if (msgLength != -1) {
				byte[] bytes = new byte[msgLength];
				dataInput.read(bytes);
				this.phoneNumber = new String(bytes);
			}		

	}

	@Override
	public void writeMessage() throws Exception {
		DataOutputStream dataOutput = getDataOutPutStream();
		dataOutput.writeInt(MessageType.REGISTER_MESSAGE.getValue());
		byte[] phoneNumberByteList = phoneNumber.getBytes();
		int phoneNumberLength = phoneNumberByteList.length;
		Log.i("info", phoneNumberLength + "");
		dataOutput.writeInt(phoneNumberLength);
		dataOutput.write(phoneNumberByteList);
		dataOutput.flush();
		Log.i("info", "send successful!");
	}
}
