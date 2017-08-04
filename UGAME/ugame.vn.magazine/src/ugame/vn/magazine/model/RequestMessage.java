package ugame.vn.magazine.model;

import java.net.Socket;
import java.util.List;

import ugame.vn.magazine.event.ClientSocketHandler;

public class RequestMessage extends Message {
	private ClientSocketHandler clientHandler;

	public List<String> getParamContents() {
		return paramContents;
	}

	public void setParamContents(List<String> paramContents) {
		this.paramContents = paramContents;
	}

	private List<String> paramNames;
	private List<String> paramContents;

	public RequestMessage(ClientSocketHandler client) throws Exception {
		this.clientHandler = client;
		init();
	}

	private void init() throws Exception {
		Socket socket = clientHandler.getClientSocket();
		setDataInputStream(socket.getInputStream());
		setDataOutPutStream(socket.getOutputStream());
	}

	@Override
	public void readMessage() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeMessage() throws Exception {
		// TODO Auto-generated method stub

	}

}