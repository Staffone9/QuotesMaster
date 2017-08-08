package com.example.staffonechristian.quotesmaster;

import java.util.ArrayList;

/**
 * Created by staffonechristian on 2017-07-18.
 */

public class UserData {

    public static String userEmailID;
    public static ArrayList<String> userLikedQuotes = new ArrayList<String>();
    public static ArrayList<String> userViewedQuotes = new ArrayList<String>();
    public static ArrayList<String> userFavouriteAuthor = new ArrayList<String>();
    public static ArrayList<String> userFavoriteCategories = new ArrayList<String>();
    public static int lastPosition;
    public UserData(String emailId){
        userEmailID = emailId;
//        userLikedQuotes.add("empty");
//        userViewedQuotes.add("empty");
//        userFavouriteAuthor.add("empty");
//        userFavoriteCategories.add("empty");
    }

    public UserData(){

    }
    public String getUserEmailID() {
        return userEmailID;
    }

    public void setUserEmailID(String userEmailID) {
        this.userEmailID = userEmailID;
    }

    public ArrayList<String> getUserLikedQuotes() {
        return userLikedQuotes;
    }

    public void setUserLikedQuotes(ArrayList<String> userLikedQuotes) {
        this.userLikedQuotes = userLikedQuotes;
    }

    public ArrayList<String> getUserViewedQuotes() {
        return userViewedQuotes;
    }

    public void setUserViewedQuotes(ArrayList<String> userViewedQuotes) {
        this.userViewedQuotes = userViewedQuotes;
    }

    public ArrayList<String> getUserFavouriteAuthor() {
        return userFavouriteAuthor;
    }

    public void setUserFavouriteAuthor(ArrayList<String> userFavouriteAuthor) {
        this.userFavouriteAuthor = userFavouriteAuthor;
    }

    public ArrayList<String> getUserFavoriteCategories() {
        return userFavoriteCategories;
    }

    public void setUserFavoriteCategories(ArrayList<String> userFavoriteCategories) {
        this.userFavoriteCategories = userFavoriteCategories;
    }
}
