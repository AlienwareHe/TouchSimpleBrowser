package com.alien.touch.browser;

import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class InjectedChromeClient extends WebChromeClient {


    private static MainActivity activity;

    public InjectedChromeClient(MainActivity activity) {
        this.activity = activity;
        Log.i(Constants.TAG, "activity:" + activity);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        //为什么要在这里注入JS
        //1 OnPageStarted中注入有可能全局注入不成功，导致页面脚本上所有接口任何时候都不可用
        //2 OnPageFinished中注入，虽然最后都会全局注入成功，但是完成时间有可能太晚，当页面在初始化调用接口函数时会等待时间过长
        //3 在进度变化时注入，刚好可以在上面两个问题中得到一个折中处理
        //为什么是进度大于25%才进行注入，因为从测试看来只有进度大于这个数字页面才真正得到框架刷新加载，保证100%注入成功
        if (newProgress > 20) {
            //加载script标签
//            String jsCode = "var newscript = document.createElement(\"script\");";
//            jsCode += "newscript.src='https://unpkg.com/ajax-hook/dist/ajaxhook.min.js';";
//            jsCode += "document.body.appendChild(newscript);";
//            jsCode += "newscript.onload=function(){window.alert('process');};";
//            view.loadUrl("javascript:" + jsCode);

            //注入JS
//            InputStream is = activity.getAssets().open("js/mmy.js");
//            int length = is.available();
//            byte[] buffer = new byte[length];
//            is.read(buffer);
//            view.loadUrl("javascript:" + new String(buffer, "utf8"));
        }
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.i(Constants.TAG, "js console message:" + consoleMessage.message());
        super.onConsoleMessage(consoleMessage);
        return true;
    }
}
