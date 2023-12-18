package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    // 抽卡网址
    private String URL = "https://wishsimulator.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取WebView控件
        WebView mw = findViewById(R.id.myweb);

        // 获取WebSettings对象，启用JavaScript和支持缩放
        WebSettings webSettings = mw.getSettings();
        webSettings.setJavaScriptEnabled(true);  // 支持JavaScript
        webSettings.setDomStorageEnabled(true); // 支持CSS
        webSettings.setSupportZoom(true);      // 支持缩放
        webSettings.setBuiltInZoomControls(true);

        // 调用成员函数访问网页，加载资源
        mw.loadUrl(URL);

        // 设置WebViewClient以在WebView中加载URL
        mw.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写WebViewClient的shouldOverrideUrlLoading()方法
                // 使用WebView加载显示url
                view.loadUrl(url);
                return true;
            }
        });
    }
}