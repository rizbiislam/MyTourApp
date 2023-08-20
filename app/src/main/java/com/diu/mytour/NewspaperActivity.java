package com.diu.mytour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;

public class NewspaperActivity extends AppCompatActivity {
    private WebView wv;
    String url="https://en.prothomalo.com";
    LinearLayout l1,l2;
    Button btn;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper);


        layout=(RelativeLayout)findViewById(R.id.layout);
        l1=(LinearLayout)findViewById(R.id.l1);
        l2=(LinearLayout)findViewById(R.id.l2);
        btn=(Button) findViewById(R.id.btn11);
        l1.setVisibility(View.VISIBLE);
        l2.setVisibility(View.INVISIBLE);

        wv = (WebView) findViewById(R.id.webview);
        wv.setWebViewClient(new mybroswer());
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wv.getSettings().setUserAgentString("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.137 Safari/537.36");
        wv.loadUrl(url);

        if (!isNetworkAvailable()) {

            l1.setVisibility(View.INVISIBLE);
            l2.setVisibility(View.VISIBLE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isNetworkAvailable()) {

                        Snackbar.make(layout,"Check your internet connection!", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(getResources().getColor(R.color.white))
                                .setTextColor(getResources().getColor(R.color.white))
                                .setBackgroundTint(getResources().getColor(R.color.colorAccent))
                                .show();
                    }
                    else {
                        if (isNetworkAvailable()) {

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    l1.setVisibility(View.VISIBLE);


                                }
                            }, 8000);
                            l2.setVisibility(View.GONE);

                            wv = (WebView) findViewById(R.id.webview);
                            wv.setWebViewClient(new mybroswer());
                            wv.getSettings().setUseWideViewPort(true);
                            wv.getSettings().setJavaScriptEnabled(true);
                            wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                            wv.loadUrl(url);
                        }
                    }
                }
            });

        }
        else {
            wv.setVisibility(View.VISIBLE);

            l1.setVisibility(View.VISIBLE);
            l2.setVisibility(View.INVISIBLE);

            fileList();

        }



    }

    public class mybroswer extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

            l1.setVisibility(View.INVISIBLE);
            l2.setVisibility(View.VISIBLE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isNetworkAvailable()) {

                        Snackbar.make(layout,"Check your internet connection!", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(getResources().getColor(R.color.white))
                                .setTextColor(getResources().getColor(R.color.white))
                                .setBackgroundTint(getResources().getColor(R.color.colorAccent))
                                .show();

                    } else {
                        if (isNetworkAvailable()) {

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    l1.setVisibility(View.VISIBLE);


                                }
                            }, 8000);
                            l2.setVisibility(View.GONE);

                            wv = (WebView) findViewById(R.id.webview);
                            wv.setWebViewClient(new mybroswer());
                            wv.getSettings().setUseWideViewPort(true);
                            wv.getSettings().setJavaScriptEnabled(true);
                            wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                            wv.loadUrl(url);
                        }
                    }
                }
            });
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}