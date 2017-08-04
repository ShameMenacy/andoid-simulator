package vn.ugame.entity;

@Entity
public class Answer {
    @Column(name = "answerId")
    private int answerId;
    @Column(name = "questionId")
    private int questionId;
    @Column(name = "answerContent")
    private String answerContent;   
    @Column(name = "status")
    private int status;

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Answer() {
    }
}
