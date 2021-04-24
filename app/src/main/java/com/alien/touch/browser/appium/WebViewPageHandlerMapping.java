package com.alien.touch.browser.appium;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.alien.touch.browser.Constants;

import java.util.ArrayList;
import java.util.List;

public class WebViewPageHandlerMapping {

    private static List<WebViewPageHandler> webViewPageHandlers = new ArrayList<>();

    static {
        webViewPageHandlers.add(new HotelListPageHandler());
        webViewPageHandlers.add(new CityListPageHandler());
        webViewPageHandlers.add(new DemoCoursePageHandler());
    }

    public static void dispatch(String url, View rootView, WebView webView) {
        if (webView == null || url == null) {
            return;
        }
        String realUrl = url.substring(0, url.contains("?") ? url.indexOf("?") : url.length());
        for (WebViewPageHandler webViewPageHandler : webViewPageHandlers) {
            if (webViewPageHandler.supports(realUrl, url)) {
                Log.i(Constants.TAG,"handle webview page:" + webViewPageHandler);
                webViewPageHandler.handle(rootView, webView);
                return;
            }
        }
    }
}
