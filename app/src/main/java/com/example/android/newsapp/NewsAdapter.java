package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tzouanakos on 15/03/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, int resource, List<News> newsObjects) {
        super(context, 0, newsObjects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        News currentNews = getItem(position);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.title_textview);
        titleTextView.setText(currentNews.getmWebTitle());
        TextView sectionIdTextview = (TextView) convertView.findViewById(R.id.section_textview);
        sectionIdTextview.setText(currentNews.getmSectionId());
        return convertView;
    }
}
