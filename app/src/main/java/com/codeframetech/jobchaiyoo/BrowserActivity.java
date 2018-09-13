package com.codeframetech.jobchaiyoo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.codeframetech.jobchaiyoo.R;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        WebView webView = findViewById(R.id.webView);
        String url= getIntent().getStringExtra("LINK");
        //webView.setWebViewClient(new WebViewClient());
        //WebSettings webSettings = webView.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);

    }
}
