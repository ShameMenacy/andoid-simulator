/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author TruongDV
 */
public class SuccessfulMessage extends Message {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void readMessage() {
        DataInputStream dataInput = getDataInputStream();
        try {
            int msgLength = dataInput.readInt();
            if (msgLength != -1) {
                byte[] bytes = new byte[msgLength];
                dataInput.read(bytes);
                this.message = new String(bytes);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void writeMessage() {
        DataOutputStream dataOutput = getDataOutPutStream();
        try {
            dataOutput.writeInt(MessageType.SUCCESSFUL_MESSAGE.getValue());
            byte[] messageBytes = message.getBytes();
            int messageLength = messageBytes.length;
            dataOutput.writeInt(messageLength);
            dataOutput.write(messageBytes);
            dataOutput.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
