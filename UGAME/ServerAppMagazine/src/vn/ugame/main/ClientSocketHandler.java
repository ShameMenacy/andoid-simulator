/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.main;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import vn.ugame.message.Message;
import vn.ugame.message.MessageType;
import static vn.ugame.message.MessageType.LOGIN_MESSAGE;
import static vn.ugame.message.MessageType.QUESTION_MESSAGE;
import static vn.ugame.message.MessageType.REGISTER_MESSAGE;
import static vn.ugame.message.MessageType.USER_ANSWER_MESSAGE;
import vn.ugame.module.LoginModule;
import vn.ugame.module.QuestionModule;
import vn.ugame.module.RegisterModule;

/**
 *
 * @author TruongDV
 */
public class ClientSocketHandler {

    private Socket clientSocket;
    private boolean closed;
    private MessageListener messageListener;
    private CloseSocketListener closeSocketListener;
    private Message msg;

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public ClientSocketHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.closed = false;
    }

    public void setMesasgeListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void setCloseSocketListner(CloseSocketListener closeSocketListener) {
        this.closeSocketListener = closeSocketListener;
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
                closeSocketListener.onCloseSocket(this);
                break;
            }

            try {
                readMessage();
                messageListener.onMessage(this, msg);
            } catch (Exception ex) {
                this.closed = true;
                closeSocketListener.onCloseSocket(this);
                ex.printStackTrace();
            }
        }

    }

    public void sendMessage(Message msg) throws IOException {
        //msg.writeMessage();
    }

    public void close() throws IOException {
        clientSocket.close();
        this.closed = true;
    }

    public void readMessage() throws Exception {
        InputStream inputStream = clientSocket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int msgType = dataInputStream.readInt();
        MessageType type = MessageType.getMessageType(msgType);
        System.out.println("type " + type.getValue());
        QuestionModule questionModule = new QuestionModule(this);
        switch (type) {
            case REGISTER_MESSAGE:
                RegisterModule registerModule = new RegisterModule(this);
                registerModule.execute();
                break;
            case LOGIN_MESSAGE:
                LoginModule loginModule = new LoginModule(this);
                loginModule.execute();
                break;
            case QUESTION_MESSAGE:
                questionModule.execute(QUESTION_MESSAGE);
                break;            
            case USER_ANSWER_MESSAGE:
                questionModule.execute(USER_ANSWER_MESSAGE);
                break;
        }
    }
}
