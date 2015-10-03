package com.sreesharp.webviewdemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sreesharp.webviewdemo.R;
import com.sreesharp.webviewdemo.adapters.EntryCursorAdapter;
import com.sreesharp.webviewdemo.utilities.DbHelper;

//Fragment to host the list view to display the entries from local SQLite db
public class ListviewFragment extends Fragment {

    private EntryCursorAdapter entryAdapter;
    public ListviewFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView lvEntries = (ListView) view.findViewById(R.id.lvEntries);
        entryAdapter = new EntryCursorAdapter(getContext(),DbHelper.getInstance(getContext()).getAllEntriesCursor() );
        lvEntries.setAdapter(entryAdapter);
        return view;
    }

    //Refresh the ListView by changing the current cursor
    public void refresh(){
        entryAdapter.changeCursor(DbHelper.getInstance(getContext()).getAllEntriesCursor());
    }
}
