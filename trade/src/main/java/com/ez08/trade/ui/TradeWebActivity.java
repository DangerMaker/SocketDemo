package com.ez08.trade.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ez08.trade.Constant;
import com.ez08.trade.R;


public class TradeWebActivity extends BaseActivity implements View.OnClickListener {

    ProgressBar progressBar;
    TextView txTitle;
    WebView webView;

    String title;
    String url;
    String action;

    Context mContext;
    MyWebChromeClient chromeClient;
    MyWebViewClientent clientent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_web);
        mContext = this;
        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
            url = intent.getStringExtra("url");
            action = intent.getStringExtra("action");
        }
        url = Constant.getWebUrl();
//        url = "https://www.jianshu.com/p/9be58ee20dee";
        progressBar = findViewById(R.id.progress);
        findViewById(R.id.img_back).setOnClickListener(this);
        txTitle = findViewById(R.id.title);
        webView = findViewById(R.id.wv);

        chromeClient = new MyWebChromeClient();
        clientent = new MyWebViewClientent();

        initwidget();
        txTitle.setText(title);
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_back) {
           finish();
        }
    }

    private void initwidget() {
        // TODO Auto-generated method stub
        webView.getSettings().setDefaultTextEncodingName("gb2312");
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);// 允许DCOM
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setSaveFormData(false);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebViewClient(clientent);
        webView.setWebChromeClient(chromeClient);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.e("progress", newProgress + "");
            progressBar.setProgress(newProgress);
            if (newProgress >= 90) {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    public class MyWebViewClientent extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

}
