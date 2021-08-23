package com.globomed.newsfragments;

public class Article {
    private String mImageUrl;
    private String mTitle;
    private String mDescription;
    private String mContentUrl;

    public Article(String mImageUrl, String mTitle, String mDescription, String mContentUrl) {
        this.mImageUrl = mImageUrl;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mContentUrl = mContentUrl;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getContentUrl() {
        return mContentUrl;
    }
}
