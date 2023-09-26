package com.example.ecommerce_mobile_app.model.request;

import java.io.Serializable;

public class SendReviewRequest implements Serializable {
    private String headline;
    private String comment;
    private int rating;

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
