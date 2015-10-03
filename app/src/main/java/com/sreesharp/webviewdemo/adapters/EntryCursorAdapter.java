package com.sreesharp.webviewdemo.adapters;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sreesharp.webviewdemo.R;

//Adapter resposible for providing data to the ListView
public class EntryCursorAdapter extends CursorAdapter {
    public EntryCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor,true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_entry, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvValue = (TextView) view.findViewById(R.id.tvValue);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String value = cursor.getString(cursor.getColumnIndexOrThrow("value"));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
        tvName.setText(name);
        tvValue.setText(value);
        tvDate.setText(date);
    }
}