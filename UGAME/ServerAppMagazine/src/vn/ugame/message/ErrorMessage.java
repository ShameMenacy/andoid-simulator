/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 *
 * @author TruongDV
 */
public class ErrorMessage extends Message {

    private String msgContents;

    public String getMessage() {
        return msgContents;
    }

    public void setMessage(String message) {
        this.msgContents = message;
    }

    @Override
    public void readMessage() throws IOException, EOFException {
        DataInputStream dataInput = getDataInputStream();
        int msgLength = dataInput.readInt();
        if (msgLength != -1) {
            byte[] bytes = new byte[msgLength];
            dataInput.read(bytes);
            this.msgContents = new String(bytes);
        }
    }

    @Override
    public void writeMessage() throws IOException, EOFException{
        DataOutputStream dataOutput = getDataOutPutStream();
        dataOutput.writeInt(MessageType.ERROR_MESSAGE.getValue());
        byte[] messageBytes = msgContents.getBytes();
        int messageLength = messageBytes.length;
        dataOutput.writeInt(messageLength);
        dataOutput.write(messageBytes);
        dataOutput.flush();
    }
}
