package com.example.staffonechristian.quotesmaster;

import java.util.ArrayList;

/**
 * Created by staffonechristian on 2017-07-11.
 */

public class QuoteData {

    private String quote;
    private String author;
    private String category;
    private int quoteLikes;
    private int quoteViews;
    private float priorityScore;

    public QuoteData(){

    }

    public QuoteData(String mQuoteText, String mAuthor){
        this.quote = mQuoteText;
        this.author = mAuthor;

    }
    static ArrayList<String> listOfCategory = new ArrayList<String>();

    public int getQuoteLikes() {
        return quoteLikes;
    }

    public void setQuoteLikes(int quoteLikes) {
        this.quoteLikes = quoteLikes;
    }

    public int getQuoteViews() {
        return quoteViews;
    }

    public void setQuoteViews(int quoteViews) {
        this.quoteViews = quoteViews;
    }

    public float getPriorityScore() {
        return priorityScore;
    }

    public void setPriorityScore(float priorityScore) {
        this.priorityScore = priorityScore;
    }

    public static ArrayList<String> getListOfCategory() {
        return listOfCategory;
    }

    public static void setListOfCategory(ArrayList<String> listOfCategoryOne) {
        listOfCategory = listOfCategoryOne;
    }



    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String categories) {
        this.category = categories;
    }
}
