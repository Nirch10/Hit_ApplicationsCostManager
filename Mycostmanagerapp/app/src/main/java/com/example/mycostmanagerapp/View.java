package com.example.mycostmanagerapp;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class View {

    private WebView webView;

    public View(WebView webView) {

        this.webView = webView;
        this.webView.setWebChromeClient(new WebChromeClient());
        //this.webView.setWebViewClient(new WebViewClient());
        this.webView.loadUrl("file:///android_asset/incomeoutcome.html");
        this.webView.getSettings().setJavaScriptEnabled(true);



    }

    public WebView getWebView() {
        return webView;
    }



    }

