package com.example.hw8_5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<Day> {
    public HistoryAdapter(Context context, ArrayList<Day> days) {
        super(context, 0, days);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Day day = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.daylayout, parent, false);
        }

        TextView dayText = (TextView) convertView.findViewById(R.id.dayText);
        TextView tempText = (TextView) convertView.findViewById(R.id.tempText);

        dayText.setText("Day " + day.day);
        tempText.setText(day.temp + " F");

        return convertView;
    }
}
