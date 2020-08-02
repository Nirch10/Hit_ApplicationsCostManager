package com.example.mycostmanagerapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.mycostmanagerapp.ModelView.ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.mycostmanagerapp.View;
import com.example.mycostmanagerapp.ModelView.ViewModel;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webView;
        View view = new View(new WebView(this));
        ViewModel viewModel = new ViewModel(view);
        webView = view.getWebView();
        webView.addJavascriptInterface(viewModel,"viewModel");
        //viewModel.Init();
        setContentView(webView);




    }


}







