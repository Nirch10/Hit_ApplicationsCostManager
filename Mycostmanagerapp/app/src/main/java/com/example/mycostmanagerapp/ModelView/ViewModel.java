package com.example.mycostmanagerapp.ModelView;

import android.app.Activity;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.Log;
import android.webkit.WebView;
import com.example.mycostmanagerapp.View;



public class ViewModel extends Activity implements IViewModel {



    private View view;
    private ExecutorService service;


    public ViewModel(View view) {
        this.view = view;
    }


    @android.webkit.JavascriptInterface
    public void Init() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.getWebView().evaluateJavascript("initAllData()", null);
            }
        });
    }




    /*
    @android.webkit.JavascriptInterface
    public void initcurrentuser(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.getWebView().evaluateJavascript("getDetailsFromServer()", null);
            }
        });



    }
     */



/*
    @android.webkit.JavascriptInterface
    public void addOutCome(String retail,float cost,String description) {
       // final JSONObject jsonForSend = ()
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.getWebView().evaluateJavascript("sendretail()", null);
            }
        });
    }

 */


/*
    @android.webkit.JavascriptInterface
    public void addOutCome() {
        // final JSONObject jsonForSend = ()
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.getWebView().evaluateJavascript("displayExpenses()", null);
            }
        });
    }
*/




/*
    @android.webkit.JavascriptInterface
    public int showDe(){
        //view.getWebView().evaluateJavascript("displayExpenses()",null);
        return 1;
    } */



}
