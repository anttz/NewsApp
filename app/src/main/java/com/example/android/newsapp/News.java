package com.example.android.newsapp;

/**
 * Created by tzouanakos on 15/03/2017.
 */

public class News {

    private String mSectionId;
    private String mWebTitle;
    private String mWebUrl;

    public News(String mWebTitle, String mSectionId, String mWebUrl) {
        this.mWebTitle = mWebTitle;
        this.mSectionId = mSectionId;
        this.mWebUrl = mWebUrl;
    }

    public String getmWebTitle() {
        return mWebTitle;
    }

    public String getmSectionId() {
        return mSectionId;
    }

    public String getmWebUrl() {
        return mWebUrl;
    }
}
