package com.example.ecommerce_mobile_app.model.request;

import java.io.Serializable;

public class PostQuestionRequest implements Serializable {
    private String questionContent;

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }
}
