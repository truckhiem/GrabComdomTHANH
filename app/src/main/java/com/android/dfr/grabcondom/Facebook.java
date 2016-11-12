package com.android.dfr.grabcondom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by MinhThanh on 10/27/16.
 */

public class Facebook extends Activity {

    private WebView webView;
    private static final String TAG = "Facebook";
    private ProgressDialog progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fb_layout);

//        getSupo.setDisplayHomeAsUpEnabled(true);

        this.webView = (WebView) findViewById(R.id.webFb);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        progressBar = ProgressDialog.show(Facebook.this, "Facebook Grabcondom", "Loading...");

        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url){
                Log.i(TAG, "Finished loading url: "+url);
                if (progressBar.isShowing()){
                    progressBar.dismiss();
                }
            }
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                Log.e(TAG, "Error: " + description);
//                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
            }
        });
        webView.loadUrl("https://www.facebook.com/GrabCondom/?fref=ts");
    }
}
