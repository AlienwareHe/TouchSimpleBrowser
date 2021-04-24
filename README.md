# 自制WebView浏览器实现移动端H5网页抓取

对于一个H5页面来说，如果协议已经无法摆脱风控，或者没有足够的时间去破解协议，需要在真实设备上打开浏览器访问H5网页才能实现数据抓取，我们又没有逆向或控制浏览器的能力，没有办法拦截到报文或者页面数据，我们此时应该如何解决呢。

WebView只是一个控件，浏览器可以实现，我们也可以自己实现，并且WebView当中还有许多API可以被我们逆向利用，对方网页运行在我们定义的WebView当中，那么是否可以调试将由我们自己决定，JS可以随意注入，网络请求也能随便拦截，通过JS自动点击也可以实现页面自动化，就可以实现真机渠道的移动端H5抓取。

# JS Xpath自动化点击

1. 实现com.alien.touch.browser.appium.WebViewPageHandler
2. com.alien.touch.browser.appium.WebViewPageHandlerMapping中添加实现的PageHandler

XPATH DEMO: com.alien.touch.browser.appium.DemoCoursePageHandler

# JS注入
com.alien.touch.browser.InjectedChromeClient.onProgressChanged

# JS Bridge
com.alien.touch.browser.JsInterface

通过JS注入 hook ajax和JS Bridge实现对方JS层数据拦截并回传至Java层。
