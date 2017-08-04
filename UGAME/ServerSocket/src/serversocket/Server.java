/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serversocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TruongDV
 */
public class Server {

    public static void main(String arg[]) {
        try {
            ServerSocket serverSocket = new ServerSocket(6000);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                if (clientSocket != null) {
                    InputStream input = clientSocket.getInputStream();
                    BufferedReader buff = new BufferedReader(new InputStreamReader(input));
                    String message = buff.readLine();
                    System.out.println("Message: " + message);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
