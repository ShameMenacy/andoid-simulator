/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ugame.vn.magazine.event;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import ugame.vn.magazine.model.ErrorMessage;
import ugame.vn.magazine.model.Message;
import ugame.vn.magazine.model.MessageType;
import ugame.vn.magazine.model.QuestionMessage;
import ugame.vn.magazine.model.SuccessfulMessage;
import android.util.Log;

/**
 * 
 * @author big
 */
public class ClientSocketHandler {

	private Message msg;
	private boolean closed;
	private Socket clientSocket;
	private MessageListener messageListener;
	private ConnectionErrorListener connErrorListener;

	public Message getMsg() {
		return msg;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public ClientSocketHandler(Socket clientSocket) throws Exception {
		this.clientSocket = clientSocket;
		this.closed = false;
	}

	public void setMesasgeListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public void setConnErrorListener(ConnectionErrorListener connErrorListener) {
		this.connErrorListener = connErrorListener;
	}

	public void listen() {
		Thread listenMessageThread = new Thread(new Runnable() {
			@Override
			public void run() {
				listenMessage();
			}
		});
		listenMessageThread.start();
	}

	public void listenMessage() {
		while (true) {

			if (this.closed) {
				break;
			}
			Log.i("Message", "Wait message ...");
			try {
				readMessage();
				messageListener.onMessage(this, msg);
			} catch (Exception ex) {
				Log.e("error", ex.getMessage(), ex);
				connErrorListener.onConnectionError(this);
				this.closed = true;
			}
		}
	}

	public void sendMessage() {
		try {
			msg.writeMessage();
		} catch (Exception ex) {
			connErrorListener.onConnectionError(this);
			Log.e("error", ex.getMessage());
		}
	}

	public void close() throws IOException {
		clientSocket.close();
		this.closed = true;
	}

	public void readMessage() throws Exception {
		InputStream inputStream = clientSocket.getInputStream();
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		int msgType = MessageType.UNKNOW_MESSAGE.getValue();
		msgType = dataInputStream.readInt();
		MessageType type = MessageType.getMessageType(msgType);

		Log.i("type", type.getValue() + "");

		switch (type) {
		case ERROR_MESSAGE:
			msg = new ErrorMessage(this);
			msg.readMessage();
			break;
		case SUCCESSFUL_MESSAGE:
			msg = new SuccessfulMessage(this);
			msg.readMessage();
			break;
		case QUESTION_MESSAGE:
			msg = new QuestionMessage(this);
			msg.readMessage();
		default:
			break;
		}

	}

}
