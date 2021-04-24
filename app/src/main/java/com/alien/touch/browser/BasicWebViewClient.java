package com.alien.touch.browser;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.alien.touch.browser.appium.WebViewPageHandlerMapping;

public class BasicWebViewClient extends WebViewClient {

    MainActivity activity;

    public BasicWebViewClient(MainActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        Log.i(Constants.TAG, "intercept request url:" + url);
        return super.shouldInterceptRequest(view, request);
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest resourceRequest) {
        parseJSParams(view, resourceRequest.getUrl().toString());
        return true;
    }

    private void parseJSParams(WebView view, String url) {

    }


    @Override
    public void onPageFinished(WebView view, String url) {
        try {
            // you can do something like get/set/change cookie
            CookieSyncManager.createInstance(activity);
            CookieManager cookieManager = CookieManager.getInstance();
            String cookie = cookieManager.getCookie(url);
            Log.i(Constants.TAG, "onPageFinished，url： " + url + " ---- cookie :" + cookie);

            CookieSyncManager.getInstance().sync();

            // 分发不同页面的逻辑处理
            new Thread(() -> WebViewPageHandlerMapping.dispatch(url, view, view)).start();
        } catch (Exception ex) {
            Log.e(Constants.TAG, "onPageFinished error: ", ex);
        } finally {
            super.onPageFinished(view, url);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        // you can do something like get/set/change cookie
    }


}
