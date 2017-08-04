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

/**
 * 
 * @author big
 */
public class SuccessfulMessage extends Message {

	private String message;
	private ClientSocketHandler clientHandler;
	
	public SuccessfulMessage(ClientSocketHandler client) throws IOException{			
		this.clientHandler = client;
		init();		
	}
	
	private void init()throws IOException{
		setMsgType(MessageType.SUCCESSFUL_MESSAGE);		
		Socket socket = clientHandler.getClientSocket();
		setDataInputStream(socket.getInputStream());
		setDataOutPutStream(socket.getOutputStream());
	}
		
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void readMessage() throws Exception {
		DataInputStream dataInput = getDataInputStream();		
			int msgLength = dataInput.readInt();
			if (msgLength != -1) {
				byte[] bytes = new byte[msgLength];
				dataInput.read(bytes);
				this.message = new String(bytes);
			}		
	}

	@Override
	public void writeMessage() throws Exception {
		DataOutputStream dataOutput = getDataOutPutStream();		
			dataOutput.writeInt(MessageType.SUCCESSFUL_MESSAGE.getValue());
			byte[] messageBytes = message.getBytes();
			int messageLength = messageBytes.length;
			dataOutput.writeInt(messageLength);
			dataOutput.write(messageBytes);
			dataOutput.flush();		
	}
}
