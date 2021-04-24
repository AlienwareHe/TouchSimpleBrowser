package com.alien.touch.browser;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    /**
     * 可以自定义多个不同功能按钮
     */
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.webview);
        searchButton = findViewById(R.id.search);
        initWebView();
        initButton();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {

        JsInterface jsInterface = new JsInterface();

        WebView.setWebContentsDebuggingEnabled(true);

        mWebView.setWebChromeClient(new InjectedChromeClient(this));
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        // 注入JS Bridge
        mWebView.addJavascriptInterface(jsInterface, "Android");
        mWebView.setWebViewClient(new BasicWebViewClient(this));
        mWebView.setOnKeyListener((view, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
            }
            return false;
        });
    }

    private void initButton() {
        searchButton.setOnClickListener(view -> {
            // FIX ME
            String js = "http://course.alienhe.cn/home.html";
            mWebView.loadUrl(js);
        });
    }

}