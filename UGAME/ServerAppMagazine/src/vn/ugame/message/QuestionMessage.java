package vn.ugame.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import vn.ugame.entity.Answer;
import vn.ugame.entity.Question;
import vn.ugame.main.ClientSocketHandler;

public class QuestionMessage extends Message {

    private List<Question> questionList;
    private String phoneNumber;
    private List<String> userInstallingAppList;
    private ClientSocketHandler clientHandler;

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getUserInstallingAppList() {
        return userInstallingAppList;
    }

    public void setUserInstallingAppList(List<String> userInstallingAppList) {
        this.userInstallingAppList = userInstallingAppList;
    }

    public QuestionMessage(ClientSocketHandler client) throws Exception {
        questionList = new ArrayList<>();
        userInstallingAppList = new ArrayList<>();
        this.clientHandler = client;
        init();
    }

    private void init() throws Exception {
        setMsgType(MessageType.QUESTION_MESSAGE);
        Socket socket = clientHandler.getClientSocket();
        setDataInputStream(socket.getInputStream());
        setDataOutPutStream(socket.getOutputStream());
    }

    @Override
    public void readMessage() throws Exception {
        DataInputStream dataInput = getDataInputStream();

        //read phoneNumber
        int phoneLength = dataInput.readInt();
        byte[] phoneB = new byte[phoneLength];

        dataInput.read(phoneB);
        this.phoneNumber = new String(phoneB);
        System.out.println("QustionMessage: phoneNumber: " + phoneNumber);
        //read list Install App
        int appListSize = dataInput.readInt();
        if (appListSize > 0) {
            for (int i = 0; i < appListSize; i++) {
                int packageNameLength = dataInput.readInt();
                byte[] pakageName = new byte[packageNameLength];
                dataInput.read(pakageName);
                this.userInstallingAppList.add(new String(pakageName));
            }
        }
    }

    @Override
    public void writeMessage() throws Exception {
        DataOutputStream dataOutput = getDataOutPutStream();
        //Write message type.
        System.out.println("MessageType:" + getMsgType().getValue());
        int questionSize = questionList.size();
        System.out.println("Question Size:" + questionSize);
        for (Question q : questionList) {
            //Write question id.
            dataOutput.writeInt(q.getQuestionId());

            //Write question contents
            String questionContent = q.getQuestionContent();
            byte[] questionContentBytes = questionContent.getBytes();
            dataOutput.writeInt(questionContentBytes.length);
            dataOutput.write(questionContentBytes);

            //Write answer list.
            int answertSize = q.getAnswerList().size();
            dataOutput.writeInt(answertSize);
            for (Answer a : q.getAnswerList()) {
                dataOutput.writeInt(a.getAnswerId());
                byte[] answerContent = a.getAnswerContent().getBytes();
                dataOutput.writeInt(answerContent.length);
                dataOutput.write(answerContent);
            }
        }
        dataOutput.flush();
    }
}
