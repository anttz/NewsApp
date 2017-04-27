package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by tzouanakos on 15/03/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mWebUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        this.mWebUrl = url;
    }

    /**
     * Subclasses must implement this to take care of loading their data,
     * as per {@link #startLoading()}.  This is not called by clients directly,
     * but as a result of a call to {@link #startLoading()}.
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mWebUrl == null) {
            return null;
        }
        return QueryUtils.fetchNewsData(mWebUrl);
    }
}
