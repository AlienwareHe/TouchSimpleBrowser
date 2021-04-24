package com.alien.touch.browser.appium;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.alien.touch.browser.Constants;

public class DemoCoursePageHandler implements WebViewPageHandler {

    private static final String clickByXpathJs = "document.evaluate(\"%s\",document.body, null, XPathResult.ANY_TYPE, null).iterateNext().click();";

    @Override
    public boolean supports(String url, String entireUrl) {
        return url.contains("course.alienhe.cn/home.html");
    }

    @Override
    public void handle(View rootView, WebView webView) {
        clickByXpath(webView, "//button[@class='shownoticebutton']");
    }

    private void clickByXpath(WebView webView, String elementXpath) {
        Log.i(Constants.TAG,"xpath:" + String.format(clickByXpathJs, elementXpath));
        new Handler(Looper.getMainLooper()).post(() -> webView.loadUrl("javascript:" + String.format(clickByXpathJs, elementXpath)));
    }
}
