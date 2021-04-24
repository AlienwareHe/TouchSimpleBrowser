var realXhr = "RealXMLHttpRequest";

function hookAjax(proxy) {
    window[realXhr] = window[realXhr] || XMLHttpRequest;
    XMLHttpRequest = function () {
        var xhr = new window[realXhr];
        for (var attr in xhr) {
            var type = "";
            try {
                type = typeof xhr[attr]
            } catch (e) {
            }
            if (type === "function") {
                this[attr] = hookFunction(attr);
            } else {
                Object.defineProperty(this, attr, {
                    get: getterFactory(attr),
                    set: setterFactory(attr),
                    enumerable: true
                })
            }
        }
        this.xhr = xhr;
    };
    function getterFactory(attr) {
        return function () {
            var v = this.hasOwnProperty(attr + "_") ? this[attr + "_"] : this.xhr[attr];
            var attrGetterHook = (proxy[attr] || {})["getter"];
            return attrGetterHook && attrGetterHook(v, this) || v;
         };
    };
    function setterFactory(attr) {
        return function (v) {
            var xhr = this.xhr;
            var that = this;
            var hook = proxy[attr];
            if (typeof hook === "function") {
                xhr[attr] = function () {
                    proxy[attr](that) || v.apply(xhr, arguments);
                };
            } else {
                var attrSetterHook = (hook || {})["setter"];
                v = attrSetterHook && attrSetterHook(v, that) || v;
                try {
                    xhr[attr] = v;
                } catch (e) {
                    this[attr + "_"] = v;
                }
            }
        };
    }
    function hookFunction(fun) {
        return function () {
            var args = [].slice.call(arguments);
            if (proxy[fun] && proxy[fun].call(this, args, this.xhr)) {
                return;
            }
            return this.xhr[fun].apply(this.xhr, args);
        }
    }
    return window[realXhr];
};

hookAjax({
    responseText: {
        getter: tryParseJson2
    },
    response: {
        getter: tryParseJson2
    }
});


function tryParseJson2(v, xhr) {
    console.log("====="+v);
};