package ugame.vn.simulatorData;

import java.util.ArrayList;
import java.util.List;

import ugame.vn.magazine.entity.Answer;

public class QuestionDataProvider {
	private List<Answer> answerList1;
	private List<Answer> answerList2;
	private List<Answer> answerList3;

	public QuestionDataProvider() {
		answerList1 = new ArrayList<Answer>();
		answerList2 = new ArrayList<Answer>();
		answerList3 = new ArrayList<Answer>();
		init();
		init1();
		init2();
	}

	private void init() {
		for (int i = 0; i < 9; i++) {
			Answer answer = new Answer();
			answer.setAnswerId(i);
			answer.setAnswerContent("answer" + i);
			answerList1.add(answer);
		}
	}

	private void init1() {
		for (int i = 10; i < 19; i++) {
			Answer answer = new Answer();
			answer.setAnswerId(i);
			answer.setAnswerContent("answer" + i);
			answerList2.add(answer);
		}
	}

	private void init2() {
		for (int i = 20; i < 29; i++) {
			Answer answer = new Answer();
			answer.setAnswerId(i);
			answer.setAnswerContent("answer" + i);
			answerList3.add(answer);
		}
	}

	public List<Answer> getAnswerList1() {
		return answerList1;
	}

	public void setAnswerList1(List<Answer> answerList1) {
		this.answerList1 = answerList1;
	}

	public List<Answer> getAnswerList2() {
		return answerList2;
	}

	public void setAnswerList2(List<Answer> answerList2) {
		this.answerList2 = answerList2;
	}

	public List<Answer> getAnswerList3() {
		return answerList3;
	}

	public void setAnswerList3(List<Answer> answerList3) {
		this.answerList3 = answerList3;
	}

}
