package com.example.projetocm;

public class AllReviewedUsers {
    private String userKey;
    private String reviewedVal;

    public AllReviewedUsers(){ }
    public AllReviewedUsers(String userKey,String reviewedVal){
        this.userKey=userKey;
        this.reviewedVal=reviewedVal;
    }

    public String getReviewedVal() { return reviewedVal; }
    public String getUserKey() { return userKey; }

    public void setReviewedVal(String reviewedVal) { this.reviewedVal = reviewedVal; }
    public void setUserKey(String userKey) { this.userKey = userKey; }
}
