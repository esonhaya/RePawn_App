package com.example.systemscoreinc.repawn.FeedbackRatings;

public class Feedback_Ratings_List {
    private String review_name;
    private String review_date;
    private String review_content;
    private String review_bar;
    private String review_image;

    public Feedback_Ratings_List(String review_name, String review_date, String review_content, String review_bar,
                                 String review_image) {
        this.review_name = review_name;
        this.review_date = review_date;
        this.review_content = review_content;
        this.review_bar = review_bar;
        this.review_image = review_image;
    }


    public String getReview_image() {
        return review_image;
    }

    public String getReview_name() {
        return review_name;
    }

    public String getReview_date() {
        return review_date;
    }

    public String getReview_content() {
        return review_content;
    }

    public String getReview_bar() {
        return review_bar;
    }
}
