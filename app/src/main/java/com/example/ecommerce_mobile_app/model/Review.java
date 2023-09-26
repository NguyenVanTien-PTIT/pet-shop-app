package com.example.ecommerce_mobile_app.model;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable {
    private String headline;
    private String comment;
    private int rating;
    private int votes;
    private String reviewTime;
    private String reviewerFullName;
    private String reviewerPhotoImagePath;

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

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getReviewerFullName() {
        return reviewerFullName;
    }

    public void setReviewerFullName(String reviewerFullName) {
        this.reviewerFullName = reviewerFullName;
    }

    public String getReviewerPhotoImagePath() {
        return reviewerPhotoImagePath;
    }

    public void setReviewerPhotoImagePath(String reviewerPhotoImagePath) {
        this.reviewerPhotoImagePath = reviewerPhotoImagePath;
    }
}
