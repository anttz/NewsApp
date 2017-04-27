package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tzouanakos on 15/03/2017.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    private static URL createWebUrl(String stringUrl) {
        if (stringUrl == null) {
            return null;
        }
        URL webUrl = null;
        try {
            webUrl = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return webUrl;
    }

    private static String makeHttpNewsRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving news json response ", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilderOutput = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader((inputStreamReader));
            String stringLine = bufferedReader.readLine();
            while (stringLine != null) {
                stringBuilderOutput.append(stringLine);
                stringLine = bufferedReader.readLine();
            }
        }
        return stringBuilderOutput.toString();
    }

    private static List<News> extractFromJSON(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        List<News> newsList = new ArrayList<>();
        try {
            JSONObject baseJSONObject = new JSONObject(jsonResponse);
            JSONObject jsonObject = baseJSONObject.getJSONObject("response");
            JSONArray resultsArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject currentNews = resultsArray.getJSONObject(i);
                String currentSectionId = currentNews.getString("sectionId");
                String currentWebTitle = currentNews.getString("webTitle");
                String currentWebUrl = currentNews.getString("webUrl");
                News news = new News(currentWebTitle, currentSectionId, currentWebUrl);
                newsList.add(news);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON response ", e);
        }
        return newsList;
    }

    public static List<News> fetchNewsData(String requestUrl) {
        URL url = createWebUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpNewsRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem occured while making HTTP request");
        }
        List<News> newsList = extractFromJSON(jsonResponse);
        return newsList;
    }
}
