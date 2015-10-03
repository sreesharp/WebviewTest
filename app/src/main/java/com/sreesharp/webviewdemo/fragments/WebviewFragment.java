package com.sreesharp.webviewdemo.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sreesharp.webviewdemo.R;
import com.sreesharp.webviewdemo.models.Entry;
import com.sreesharp.webviewdemo.utilities.DbHelper;
import com.sreesharp.webviewdemo.utilities.Utility;


//Fragment with webview to load the remote web form
public class WebviewFragment extends Fragment {

    private static final String FORM_URL = "https://dl.dropboxusercontent.com/s/7hqmaeh550bjsf3/test.html?dl=0";
    private static final String OUTLOOK_URL = "outlook://submitValues";

    private OnDataChangedListener listener;
    private ProgressBar progressBar;
    private WebView webView;

    // Define the events that the fragment will use to communicate
    public interface OnDataChangedListener {
        void onDataChanged();
    }

    public WebviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        initializeWebView(view);
        loadWebPage();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnDataChangedListener) {
            listener = (OnDataChangedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement WebviewFragment.OnDataChangedListener");
        }
    }

    //Initialize the webview widget
    private void initializeWebView(View view){
        webView = (WebView) view.findViewById(R.id.webView);
        // Configure related browser settings
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setBackgroundColor(Color.WHITE);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.addJavascriptInterface(new JsHandler(), "JsHandler");
        webView.setHorizontalScrollBarEnabled(false);
        // disable scroll on touch
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
        // Configure the client to use when opening URLs
        webView.setWebViewClient(new MyBrowser());
        //TODO: Improve the webview performance
    }

    //Load the webpage from network or local cache
    private void loadWebPage(){
        if(Utility.isNetworkAvailable(getContext()))
            // Load the initial URL
            webView.loadUrl(FORM_URL);
            //TODO: Save to local cache if its not available in cache
        else{
            //TODO: load from local cache
            Toast.makeText(getContext(),"Network not available. Please check your network.", Toast.LENGTH_LONG).show();
        }
    }

    //Handler for javascript call from the Web view
    private class JsHandler {
        @JavascriptInterface
        public void processHTML(String data) {
            DbHelper.getInstance(getContext()).addEntries(Entry.getEntriesFromRawData(data));
            listener.onDataChanged();
        }
    }

    // Manages the behavior when URLs are loaded
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.equalsIgnoreCase(FORM_URL) ) {
                view.loadUrl(url);
            }
            if(url.equalsIgnoreCase(OUTLOOK_URL)) {
                Toast.makeText(getContext(), R.string.alert_adding_entries_to_db, Toast.LENGTH_LONG).show();
                view.loadUrl(Utility.getInjectionScript(getContext(),"readInput.js"));
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //TODO: Clean up the web page by manipulating the DOM elements for better user experience
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

}
