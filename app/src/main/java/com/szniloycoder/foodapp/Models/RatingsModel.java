package com.szniloycoder.foodapp.Models;

public class RatingsModel {

    public float rating;
    public String review;
    public String reviewerImg;
    public String reviewerName;

    public RatingsModel() {
    }

    public RatingsModel(float rating2, String review2, String reviewerImg2, String reviewerName2) {
        this.rating = rating2;
        this.review = review2;
        this.reviewerImg = reviewerImg2;
        this.reviewerName = reviewerName2;
    }

    public float getRating() {
        return this.rating;
    }

    public void setRating(float rating2) {
        this.rating = rating2;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review2) {
        this.review = review2;
    }

    public String getReviewerImg() {
        return this.reviewerImg;
    }

    public void setReviewerImg(String reviewerImg2) {
        this.reviewerImg = reviewerImg2;
    }

    public String getReviewerName() {
        return this.reviewerName;
    }

    public void setReviewerName(String reviewerName2) {
        this.reviewerName = reviewerName2;
    }
}
