package com.sreesharp.webviewdemo.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Utility {

    //Check whether network is available or not
    public static Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    //Read the given file contents and return as String
    public static String getInjectionScript(Context context, String fileName)  {
        StringBuilder buf = new StringBuilder();
        InputStream inject = null;// file from assets
        try {
            inject = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inject, "UTF-8"));
            String str;
            while ((str = reader.readLine()) != null) {
                buf.append(str);
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }
}
