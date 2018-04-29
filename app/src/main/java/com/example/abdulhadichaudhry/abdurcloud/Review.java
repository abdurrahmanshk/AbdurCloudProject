package com.example.abdulhadichaudhry.abdurcloud;


public class Review {
    private int rating;
    private String review;
    private String username;

    public Review() {
    }

    public Review(int rating, String review, String username) {
        this.rating = rating;
        this.review = review;
        this.username = username;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}