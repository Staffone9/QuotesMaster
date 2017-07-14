package com.example.staffonechristian.quotesmaster;

import java.util.ArrayList;

/**
 * Created by staffonechristian on 2017-07-11.
 */

public class QuoteData {

    private String quote;
    private String author;
    private String category;

    static ArrayList<String> listOfCategory = new ArrayList<String>();


    public static ArrayList<String> getListOfCategory() {
        return listOfCategory;
    }

    public static void setListOfCategory(ArrayList<String> listOfCategoryOne) {
        listOfCategory = listOfCategoryOne;
    }

    public QuoteData(){

    }

    public QuoteData(String mQuoteText, String mAuthor){
        this.quote = mQuoteText;
        this.author = mAuthor;

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
