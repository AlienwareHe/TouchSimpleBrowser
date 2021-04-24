package com.alien.touch.browser;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class JsInterface {

    /**
     * 在JS中向Logcat打印日志
     */
    @JavascriptInterface
    public void log(String tag, String msg) {
        if (tag == null || tag.length() == 0 || msg == null || msg.length() == 0) {
            return;
        }
        int segmentSize = 3 * 1024;
        long length = msg.length();
        if (length <= segmentSize) {
            Log.e(tag, msg);
        } else {
            while (msg.length() > segmentSize) {
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.i(tag, logContent);
            }
            Log.i(tag, msg);
        }

    }
}
