package com.alien.touch.browser.appium;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.alien.touch.browser.Constants;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 点击城市输入框，输入城市，选择第一个推荐城市
 */
public class CityListPageHandler implements WebViewPageHandler {

    private static AtomicBoolean isHandled = new AtomicBoolean(false);

    /**
     * 进入输入城市页面
     */
    private String clickInputCityJs = "document.evaluate(\"//div[@class='search_city_inner J_city_search_trigger']\",document.body, null, XPathResult.ANY_TYPE, null).iterateNext().click();";
    /**
     * 输入城市并聚焦，触发suggest逻辑
     */
    private String inputCityJs = "document.evaluate(\"//input[@name='keyword']\",document.body, null, XPathResult.ANY_TYPE, null).iterateNext().value='{{cityName}}';document.evaluate(\"//input[@name='keyword']\",document.body, null, XPathResult.ANY_TYPE, null).iterateNext().dispatchEvent(new Event('focus'));";
    /**
     * 点评监听的是pointerdown、up事件，通过click()等无法生效
     * 但在老版本的浏览器上可能不支持PointerEvent指针事件，此时可以转化为TouchEvent或者MouseEvent，可以具体分析绑定的事件列表
     */
    private String suggestCityClickJs = "if(window.PointerEvent){document.evaluate(\"//ul[@class='J_suggest_list suggest_list']/li[1]/a\",document.body, null, XPathResult.ANY_TYPE, null).iterateNext().dispatchEvent(new PointerEvent('pointerdown',{bubbles: true,cancelable: true,view: window}));document.evaluate(\"//ul[@class='J_suggest_list suggest_list']/li[1]/a\",document.body, null, XPathResult.ANY_TYPE, null).iterateNext().dispatchEvent(new PointerEvent('pointerup',{bubbles: true,cancelable: true,view: window}));}else{document.evaluate(\"//ul[@class='J_suggest_list suggest_list']/li[1]/a\",document.body, null, XPathResult.ANY_TYPE, null).iterateNext().dispatchEvent(new TouchEvent('touchstart',{bubbles: true,cancelable: true,view: window}));document.evaluate(\"//ul[@class='J_suggest_list suggest_list']/li[1]/a\",document.body, null, XPathResult.ANY_TYPE, null).iterateNext().dispatchEvent(new TouchEvent('touchend',{bubbles: true,cancelable: true,view: window}));console.log('不支持PointerEvent，使用TouchEvent')}";

    @Override
    public boolean supports(String url, String entireUrl) {
        return url != null && url.contains("m.dianping.com/citylist");
    }

    @Override
    public void handle(View rootView, WebView webView) {
        if (webView == null || rootView == null) {
            return;
        }
        try {
            new Handler(Looper.getMainLooper()).postDelayed(() -> internalHandle(rootView, webView), 1500);
        } catch (Throwable e) {
            Log.e(Constants.TAG, "city list page handle exception", e);
        }
    }

    private void internalHandle(View rootView, WebView webView) {
        if (!isHandled.compareAndSet(false, true)) {
            Log.i(Constants.TAG, "city list page has handled");
            return;
        }
        String city = "beijing";
        Log.i(Constants.TAG, "city page click input city JS:" + clickInputCityJs);
        webView.loadUrl("javascript:" + clickInputCityJs);
        // 输入城市
        String finalInputCityJs = inputCityJs.replaceAll("\\{\\{cityName\\}\\}", city);
        Log.i(Constants.TAG, "city page input city JS:" + finalInputCityJs);
        new Handler(Looper.getMainLooper()).postDelayed(() -> webView.loadUrl("javascript:" + finalInputCityJs), 1000);
        // 点击城市
        Log.i(Constants.TAG, "city page click suggest city JS:" + suggestCityClickJs);
        new Handler(Looper.getMainLooper()).postDelayed(() -> webView.loadUrl("javascript:" + suggestCityClickJs), 3000);
    }

}
