package ugame.vn.magazine.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import ugame.vn.magazine.entity.Answer;
import ugame.vn.magazine.entity.Question;
import ugame.vn.magazine.event.ClientSocketHandler;
import android.util.Log;

public class QuestionMessage extends Message {
	private List<Question> questionList;
	private String phoneNumber;
	private List<String> userAppList;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<String> getUserAppList() {
		return userAppList;
	}

	public void setUserAppList(List<String> userAppList) {
		this.userAppList = userAppList;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	private ClientSocketHandler clientHandler;

	public QuestionMessage(ClientSocketHandler client) throws Exception {
		this.clientHandler = client;
		questionList = new ArrayList<Question>();
		userAppList = new ArrayList<String>();
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
		DataInputStream inputStream = getDataInputStream();
		// read question Size
		int questSize = inputStream.readInt();
		// check question Size
		if (questSize != -1) {
			for (int i = 0; i < questSize; i++) {
				Question question = new Question();
				question.setQuestionId(inputStream.readInt());

				int questContLength = inputStream.readInt();
				if (questContLength != -1) {
					byte[] bs = new byte[questContLength];
					inputStream.read(bs);
					question.setQuestionContent(new String(bs));

					// read Answer
					List<Answer> answers = new ArrayList<Answer>();

					int ansSize = inputStream.readInt();
					if (ansSize != -1) {
						for (int j = 0; j < ansSize; j++) {
							Answer answer = new Answer();
							answer.setAnswerId(inputStream.readInt());

							int ansContLength = inputStream.readInt();
							if (ansContLength != -1) {
								byte[] bytes = new byte[ansContLength];
								inputStream.read(bytes);
								answer.setAnswerContent(new String(bytes));
								answers.add(answer);
							}
						}
					}
					// add answer to question
					question.setAnswerList(answers);
				}
				questionList.add(question);
			}
		}
	}

	@Override
	public void writeMessage() throws Exception {
		DataOutputStream dataOutput = getDataOutPutStream();
		// SEND MESSAGE TYPE TO SERVER
		dataOutput.writeInt(getMsgType().getValue());
		// SEND PhoneNumber
		int phoneLength = this.phoneNumber.length();
		byte[] phone = this.phoneNumber.getBytes();

		dataOutput.writeInt(phoneLength);
		dataOutput.write(phone);

		Log.i("phoneNumber", phoneNumber + "");

		// SEND userAppList
		if (this.userAppList != null) {
			int userAppListSize = this.userAppList.size();
			if (userAppListSize > 0) {
				dataOutput.writeInt(userAppListSize);
				for (String packageName : this.userAppList) {
					int packageNameLength = packageName.length();
					byte[] packName = packageName.getBytes();

					dataOutput.writeInt(packageNameLength);
					dataOutput.write(packName);
				}
			}
			Log.i("userAppList", userAppListSize + "");
		}
		dataOutput.flush();
	}
}
