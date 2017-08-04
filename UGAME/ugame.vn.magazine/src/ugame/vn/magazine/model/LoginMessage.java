/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ugame.vn.magazine.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import ugame.vn.magazine.event.ClientSocketHandler;

import android.util.Log;

/**
 * 
 * @author big
 */
public class LoginMessage extends Message {

	private String phoneNumber;
	private int activeCode;
	private ClientSocketHandler clientHandler;
	
	public LoginMessage(ClientSocketHandler clientHandler)throws Exception{
		this.clientHandler = clientHandler;
		init();
	}
	
	private void init()throws Exception{
		setMsgType(MessageType.ERROR_MESSAGE);		
		Socket socket = clientHandler.getClientSocket();
		setDataInputStream(socket.getInputStream());
		setDataOutPutStream(socket.getOutputStream());
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(int activeCode) {
		this.activeCode = activeCode;
	}

	@Override
	public void readMessage() {
		DataInputStream dataInput = getDataInputStream();
		try {

			int msgLength = dataInput.readInt();

			if (msgLength != -1) {
				byte[] bytes = new byte[msgLength];
				dataInput.read(bytes);
				this.phoneNumber = new String(bytes);
			}

			this.activeCode = dataInput.readInt();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

    @Override
    public void writeMessage() {
        DataOutputStream dataOutput = getDataOutPutStream();
        try {
            dataOutput.writeInt(MessageType.LOGIN_MESSAGE.getValue());            
            byte[] phoneNumberByteList = phoneNumber.getBytes();
            int phoneNumberLength = phoneNumberByteList.length;
            dataOutput.writeInt(phoneNumberLength);
            dataOutput.write(phoneNumberByteList);
            dataOutput.writeInt(activeCode);
            dataOutput.flush();
        } catch (IOException ex) {
            Log.e("error:",ex.getMessage());
        }
    }
}
