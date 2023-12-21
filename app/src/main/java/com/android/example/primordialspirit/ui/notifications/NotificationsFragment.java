package com.android.example.primordialspirit.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.example.primordialspirit.R;
import com.android.example.primordialspirit.databinding.FragmentNotificationsBinding;
import com.blankj.utilcode.util.FragmentUtils;

public class NotificationsFragment extends Fragment {

    private final String URL = "https://wishsimulator.app/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications,container, false);

        WebView mw = binding.myweb;

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

        return  binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}