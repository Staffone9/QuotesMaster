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
    private String key;

    @Override
    public String toString() {
        return "QuoteData{" +
                "quote='" + quote + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", quoteLikes=" + quoteLikes +
                ", quoteViews=" + quoteViews +
                ", priorityScore=" + priorityScore +
                ", key='" + key + '\'' +
                '}';
    }

    public QuoteData(){

    }

    public QuoteData(String mQuoteText, String mAuthor,String mKey,int mQuoteLikes){
        this.quote = mQuoteText;
        this.author = mAuthor;
        this.key = mKey;
        this.quoteLikes = mQuoteLikes;
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

    public String getKey(){return key;}

    public void setKey(String mKey){
        this.key = mKey;
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
