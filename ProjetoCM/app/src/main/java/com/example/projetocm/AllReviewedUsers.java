package com.example.projetocm;

public class AllReviewedUsers {
    private String userKey;
    private Double reviewedVal;

    public AllReviewedUsers(){ }
    public AllReviewedUsers(String userKey,Double reviewedVal){
        this.userKey=userKey;
        this.reviewedVal=reviewedVal;
    }

    public Double getReviewedVal() { return reviewedVal; }
    public String getUserKey() { return userKey; }

    public void setReviewedVal(Double reviewedVal) { this.reviewedVal = reviewedVal; }
    public void setUserKey(String userKey) { this.userKey = userKey; }
}
