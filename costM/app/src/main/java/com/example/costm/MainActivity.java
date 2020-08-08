package com.example.costm;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setAllowFileAccessFromFileURLs(true);
        //webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
//        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("file:///android_asset/login.html");
        webView.setWebChromeClient(new WebChromeClient());


        setContentView(webView);

    }
}
