package com.example.projetocm;

public class User {
    private static int userID = 0;
    private String username;
    private String email;
    private String password;
    private String userIdentification;
    public boolean userGuide;
    private double ratingScore;
    private int numOfPplHelped;

    public User(String username, String email, String password)
    {
        userID++;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userGuide = false;
    }

    public User(String username, String email, String password,
                String userIdentification)
    {
        userID++;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userIdentification = userIdentification;
        this.userGuide = true;
        this.ratingScore = 0;
        this.numOfPplHelped = 0;
    }

    public String getUserID()
    {
        String uID = "" + userID;
        return uID;
    }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getUserIdentification() { return userIdentification; }
    public double getRatingScore() { return ratingScore; }
    public int getNumOfPplHelped() { return numOfPplHelped; }
    public boolean isUserGuide() { return userGuide; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setUserIdentification(String userIdentification) { this.userIdentification = userIdentification; }
    public void setRatingScore(double ratingScore) { this.ratingScore = ratingScore; }
    public void setNumOfPplHelped(int numOfPplHelped) { this.numOfPplHelped = numOfPplHelped; }
}
