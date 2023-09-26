package com.example.ecommerce_mobile_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.ecommerce_mobile_app.databinding.ActivityManagerBinding;

public class ManagerActivity extends AppCompatActivity {
    private ActivityManagerBinding activityManagerBinding;
    private WebView wv_manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManagerBinding = ActivityManagerBinding.inflate(getLayoutInflater());
        setContentView(activityManagerBinding.getRoot());
        wv_manager = activityManagerBinding.wvManager;
        wv_manager.getSettings().setLoadWithOverviewMode(true);
        wv_manager.getSettings().setUseWideViewPort(true);
        wv_manager.getSettings().setJavaScriptEnabled(true);
        wv_manager.setWebViewClient(new WebViewClient());
        wv_manager.getSettings().setBuiltInZoomControls (true);
        wv_manager.getSettings().setDomStorageEnabled(true);
        wv_manager.getSettings().setDatabaseEnabled(true);
        wv_manager.getSettings().setCacheMode (WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv_manager.setWebChromeClient(new WebChromeClient());
        wv_manager.loadUrl("https://ecommerce-admin.herokuapp.com");
    }
}