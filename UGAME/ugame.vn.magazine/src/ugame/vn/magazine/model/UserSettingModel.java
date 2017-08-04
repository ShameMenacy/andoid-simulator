package ugame.vn.magazine.model;

import java.util.ArrayList;
import java.util.List;

import ugame.vn.magazine.entity.Question;

public class UserSettingModel {
	private String phoneNumber;
	private int activeCode;	
	private List<Integer> selectedAnswerList;
	private List<Question> questionList;

	public UserSettingModel() {
		selectedAnswerList = new ArrayList<Integer>();
		questionList = new ArrayList<Question>();
	}


	public void addAnswer(int answerId) {
		for (int i = 0; i < selectedAnswerList.size(); i++) {
			if (selectedAnswerList.get(i) == answerId)
				return;
		}
		selectedAnswerList.add(answerId);

	}

	public void removeAnswer(int answerId) {
		Integer answer = Integer.valueOf(answerId);
		selectedAnswerList.remove(answer);
	}

	public List<Integer> getSelectedAnswerList() {
		return selectedAnswerList;
	}

	public void setSelectedAnswerList(List<Integer> selectedAnswerList) {
		this.selectedAnswerList = selectedAnswerList;
	}

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

	public int getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(int activeCode) {
		this.activeCode = activeCode;
	}
}
