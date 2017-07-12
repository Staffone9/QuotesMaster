package com.example.staffonechristian.quotesmaster;

/**
 * Created by staffonechristian on 2017-07-11.
 */

public class QuoteData {

    private String quote;
    private String author;
    private String category;

    public QuoteData(){

    }

    public QuoteData(String mQuoteText, String mAuthor, String mCategory){
        this.quote = mQuoteText;
        this.author = mAuthor;
        this.category = mCategory;
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
