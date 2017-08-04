/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ugame.vn.magazine.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @author big
 */
public abstract class Message {

	private MessageType msgType;
	private DataInputStream dataInput;
	private DataOutputStream dataOutPut;

	public MessageType getMsgType() {
		return msgType;
	}

	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}

	public DataInputStream getDataInputStream() {
		return dataInput;
	}

	public void setDataInputStream(InputStream input) {
		this.dataInput = new DataInputStream(input);
	}

	public DataOutputStream getDataOutPutStream() {
		return dataOutPut;
	}

	public void setDataOutPutStream(OutputStream output) {
		this.dataOutPut = new DataOutputStream(output);
	}

	public abstract void readMessage()throws Exception;

	public abstract void writeMessage()throws Exception;
}
