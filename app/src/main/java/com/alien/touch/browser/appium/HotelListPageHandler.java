package com.alien.touch.browser.appium;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.alien.touch.browser.Constants;

import java.util.concurrent.atomic.AtomicInteger;

public class HotelListPageHandler implements WebViewPageHandler {

    private AtomicInteger handleCount = new AtomicInteger(0);

    @Override
    public boolean supports(String url, String entireUrl) {
        return url != null && url.contains("hotel-dp/list/list.html") && !entireUrl.contains("isSearchShow=1") && !entireUrl.contains("calendarShow=1");
    }

    @Override
    public void handle(View rootView, WebView webView) {
        try {
            Thread.sleep(3000);
            new Handler(Looper.getMainLooper()).post(() -> internalHandle(rootView, webView));
        } catch (InterruptedException e) {
            Log.e(Constants.TAG, "handle hotel list page exception", e);
        }

    }

    /**
     * 跳转页面逻辑
     */
    private void internalHandle(View rootView, WebView webView) {
        Log.i(Constants.TAG, "hotel list page handling: " + handleCount.get());
        Log.i(Constants.TAG, "跳转城市列表选择页");
        handleCount.incrementAndGet();
        webView.loadUrl("javascript: document.getElementsByClassName('search-city')[0].click();");
    }


}
