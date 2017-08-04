/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.message;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 *
 * @author TruongDV
 */
public class LoginMessage extends Message {

    private String phoneNumber;
    private int activeCode;

    public LoginMessage() {
        phoneNumber = "";
        activeCode = -1;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(int activeCode) {
        this.activeCode = activeCode;
    }

    @Override
    public void readMessage() {
        DataInputStream dataInput = getDataInputStream();
        try {
            int msgLength = -1;
            msgLength = dataInput.readInt();
            if (msgLength != -1) {
                byte[] bytes = new byte[msgLength];
                dataInput.read(bytes);
                this.phoneNumber = new String(bytes);
            }

            this.activeCode = dataInput.readInt();
        } catch (EOFException ex) {
            return;
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
            dataOutput.writeInt(activeCode);
            dataOutput.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
