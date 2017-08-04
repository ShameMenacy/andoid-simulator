package vn.ugame.entity;

import java.util.List;
@Entity
public class Question {

    @Column(name = "questionId")
    private int questionId;
    @Column(name = "questionContent")
    private String questionContent;
    private List<Answer> answerList;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public Question() {
    }
}
