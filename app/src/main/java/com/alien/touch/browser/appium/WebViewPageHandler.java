package com.alien.touch.browser.appium;

import android.view.View;
import android.webkit.WebView;

public interface WebViewPageHandler {

    /**
     *
     * @param url 不带请求参数的路径
     * @param entireUrl 完整请求路径
     * @return
     */
    boolean supports(String url, String entireUrl);

    void handle(View rootView, WebView webView);
}
