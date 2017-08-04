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
public class ErrorMessage extends Message {

    private String msgContents;
    private ClientSocketHandler clientHandler;

    public ErrorMessage(ClientSocketHandler client) throws Exception{
    	this.clientHandler = client;
    	init();
    }
    
	private void init()throws Exception{
		setMsgType(MessageType.ERROR_MESSAGE);		
		Socket socket = clientHandler.getClientSocket();
		setDataInputStream(socket.getInputStream());
		setDataOutPutStream(socket.getOutputStream());
	}
	
    public String getMessage() {
        return msgContents;
    }

    public void setMessage(String message) {
        this.msgContents = message;
    }

    @Override
    public void readMessage()throws Exception {
        DataInputStream dataInput = getDataInputStream();        
            int msgLength = dataInput.readInt();
            if (msgLength != -1) {
                byte[] bytes = new byte[msgLength];
                dataInput.read(bytes);
                this.msgContents = new String(bytes);
            }        
    }

    @Override
    public void writeMessage() {
        DataOutputStream dataOutput = getDataOutPutStream();
        try {
            dataOutput.writeInt(MessageType.ERROR_MESSAGE.getValue());
            byte[] messageBytes = msgContents.getBytes();
            int messageLength = messageBytes.length;
            dataOutput.writeInt(messageLength);
            dataOutput.write(messageBytes);
            dataOutput.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
