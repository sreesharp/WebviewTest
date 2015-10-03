package com.sreesharp.webviewdemo.models;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//Entry model
public class Entry {
    public String name;
    public String value;
    public String dateTime;

    public Entry(){
        dateTime = getDateTime();
    }

    public Entry(String name, String value){
        this.name = name;
        this.value = value;
        dateTime = getDateTime();
    }

    //Get Entry list from row data of name-value separated ¾ and ¼
    public static List<Entry> getEntriesFromRawData(String data){
        List<Entry> entries = new ArrayList<>();
        for (String nameValue : data.split("¾")) {
            int index = nameValue.indexOf("¼"); // index used to split the name-value pair
            if(index > 0) {
                Entry entry = new Entry(nameValue.substring(0,index),
                        nameValue.substring(index+1,nameValue.length()));
                entries.add(entry);
            }
        }
        return entries;
    }

    //get the current date time
    private String getDateTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
        return sdf.format(new Date());
    }
}
