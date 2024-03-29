package com.example.projetocm;

import android.location.Location;

public class User {
    //public static User loggedUser;

    private String userKey;
    private String username;
    private String email;
    private String password;
    private String userIdentification;
    public boolean userGuide;
    private double ratingScore;
    private int numOfPplHelped;
    private String imageURL;
    private int guideDistanceThreshold;

    private SimpleLocation geolocation;

    public static final int INVALID_DISTANCE = -1;

    public User() {
        // Empty constructor method, so Firebase can transfer the information over.
    }
    private boolean isAvailable;

    public User(String username, String email, String password)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userGuide = false;
        this.imageURL = "";
        this.guideDistanceThreshold = INVALID_DISTANCE;
    }

    public User(String username, String email, String password,
                String userIdentification)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userIdentification = userIdentification;
        this.userGuide = true;
        this.ratingScore = 0;
        this.numOfPplHelped = 0;
        this.imageURL = "";

        this.guideDistanceThreshold = 5000; // 5000 m <=> 5 km
    }

    public String getUserKey() {
        return userKey;
    }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getUserIdentification() { return userIdentification; }
    public double getRatingScore() { return ratingScore; }
    public int getNumOfPplHelped() { return numOfPplHelped; }
    public boolean isUserGuide() { return userGuide; }
    public String getImageURL() {
        return imageURL;
    }
    public int getGuideDistanceThreshold() {return guideDistanceThreshold;}
    public SimpleLocation getGeolocation() {return geolocation;}
    public boolean getAvailable(){ return isAvailable;}

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setUserIdentification(String userIdentification) { this.userIdentification = userIdentification; }
    public void setRatingScore(double ratingScore) { this.ratingScore = ratingScore; }
    public void setNumOfPplHelped(int numOfPplHelped) { this.numOfPplHelped = numOfPplHelped; }
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public void setGuideDistanceThreshold(int newDistance) {this.guideDistanceThreshold = newDistance;}
    public void setGeolocation(SimpleLocation geolocation) { this.geolocation = geolocation; }
    public void setAvailable(boolean isAvailable){this.isAvailable=isAvailable;}
}

