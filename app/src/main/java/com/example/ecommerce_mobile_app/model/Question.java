package com.example.ecommerce_mobile_app.model;

import java.io.Serializable;
import java.util.Date;

public class Question implements Serializable {
    private int id;
    private String questionContent;
    private String answer;
    private int votes;
    private Date askTime;
    private Date answerTime;
    private String productName;
    private String askerFullName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Date getAskTime() {
        return askTime;
    }

    public void setAskTime(Date askTime) {
        this.askTime = askTime;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAskerFullName() {
        return askerFullName;
    }

    public void setAskerFullName(String askerFullName) {
        this.askerFullName = askerFullName;
    }
}
