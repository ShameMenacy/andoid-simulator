/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import vn.ugame.message.Message;

/**
 *
 * @author TruongDV
 */
public class ServerEndPoint {

    private List<ClientSocketHandler> clientSockethandlerList;
    private ServerSocket serverSocket;
    private boolean stop;
    private int port;
    private MessageListener messageListner;

    public ServerEndPoint(int port) {
        this.port = port;
        clientSockethandlerList = new ArrayList<>();
        this.stop = true;
    }

    public void setMessageListner(MessageListener messageListner) {
        this.messageListner = messageListner;
    }

//    public void sendMessageToAll(String message) throws IOException {
//        for (ClientSocketHandler clientSocketHandler : clientSockethandlerList) {
//            clientSocketHandler.sendMessage(message + "\n");
//        }
//    }
    public void start() throws IOException {
        if (this.stop) {
            this.stop = false;
            serverSocket = new ServerSocket(port);
            Thread clientThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    createClientConnection();
                }
            });
            clientThread.start();
        }
    }

    public void createClientConnection() {
        while (true) {
            if (this.stop) {
                break;
            }

            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                if (clientSocket != null) {
                    ClientSocketHandler clientSocketHandler = new ClientSocketHandler(clientSocket);
                    clientSocketHandler.setMesasgeListener(new MessageListener() {
                        @Override
                        public void onMessage(ClientSocketHandler handler, Message msg) {
                            processClientMessage(handler, msg);
                        }
                    });

                    clientSocketHandler.setCloseSocketListner(new CloseSocketListener() {
                        @Override
                        public void onCloseSocket(ClientSocketHandler client) {                            
                            clientSockethandlerList.remove(client);
                        }
                    });

                    clientSocketHandler.listen();
                    clientSockethandlerList.add(clientSocketHandler);

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void processClientMessage(ClientSocketHandler handler, Message msg) {
        try {
            handler.sendMessage(msg);
        } catch (IOException ex) {
            Logger.getLogger(ServerEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stop() {
        this.stop = true;
    }
}
