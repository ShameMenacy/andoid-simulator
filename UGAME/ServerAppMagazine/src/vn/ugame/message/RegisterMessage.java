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
public class RegisterMessage extends Message {    
    private String phoneNumber;

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public RegisterMessage() {
    }

    @Override
    public void readMessage() {
        DataInputStream dataInput = getDataInputStream();
        try {            
            int msgLength = dataInput.readInt();
            if (msgLength != -1) {
                byte[] bytes = new byte[msgLength];
                dataInput.read(bytes);
                this.phoneNumber = new String(bytes);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void writeMessage() {
        DataOutputStream dataOutput = getDataOutPutStream();
        try {
            dataOutput.writeInt(MessageType.REGISTER_MESSAGE.getValue());
            byte[] phoneNumberByteList = phoneNumber.getBytes();
            int phoneNumberLength = phoneNumberByteList.length;
            dataOutput.writeInt(phoneNumberLength);
            dataOutput.write(phoneNumberByteList);
            dataOutput.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
