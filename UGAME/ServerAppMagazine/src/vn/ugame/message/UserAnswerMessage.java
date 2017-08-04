package vn.ugame.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import vn.ugame.main.ClientSocketHandler;

public class UserAnswerMessage extends Message {

    private String phoneNumber;
    private List<Integer> answerList;
    private ClientSocketHandler clientHandler;

    public UserAnswerMessage(ClientSocketHandler client) throws Exception {
        this.clientHandler = client;        
        init();
    }

    @Override
    public void readMessage() throws Exception {
        // TODO Auto-generated method stub
        DataInputStream dataInput = getDataInputStream();
        // get phone length;
        int phoneLength = dataInput.readInt();
        if (phoneLength != -1) {
            byte[] bPhoneNumber = new byte[phoneLength];
            dataInput.read(bPhoneNumber);
            this.phoneNumber = new String(bPhoneNumber);
        }
        System.out.println("Phone number: "+phoneNumber );
        // get message size.
        int answerNumber = dataInput.readInt();
        System.out.println("answerSize: "+answerNumber);
        for (int i = 0; i < answerNumber; i++) {
            int answerId = dataInput.readInt();
            this.answerList.add(answerId);
        }
    }

    private void init() throws Exception {
        answerList = new ArrayList<>();
        setMsgType(MessageType.USER_ANSWER_MESSAGE);
        Socket socket = clientHandler.getClientSocket();
        setDataInputStream(socket.getInputStream());
        setDataOutPutStream(socket.getOutputStream());
    }

    @Override
    public void writeMessage() throws Exception {
        // TODO Auto-generated method stub
        DataOutputStream dataOutput = getDataOutPutStream();

        // Write phone number
        byte[] bphoneNumber = this.phoneNumber.getBytes();
        dataOutput.writeInt(bphoneNumber.length);
        dataOutput.write(bphoneNumber);

        // Write answer size
        dataOutput.writeInt(this.answerList.size());

        for (int i = 0; i < answerList.size(); i++) {
            dataOutput.writeInt(answerList.get(i));
        }
        dataOutput.flush();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Integer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Integer> answerList) {
        this.answerList = answerList;
    }
}
