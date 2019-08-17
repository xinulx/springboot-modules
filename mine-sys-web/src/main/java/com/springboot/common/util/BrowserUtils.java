package com.springboot.common.util;

import javax.servlet.http.HttpServletRequest;

public class BrowserUtils {

    public enum BrowserType {
        IE11, IE10, IE9, IE8, IE7, IE6, QQBrowser, GreenBrowser, SE360, Firefox, Safari, Chrome, Opera, Gecko;
    }

    private final static String IE11 = "rv:11.0";
    private final static String IE10 = "MSIE 10.0";
    private final static String IE9 = "MSIE 9.0";
    private final static String IE8 = "MSIE 8.0";
    private final static String IE7 = "MSIE 7.0";
    private final static String IE6 = "MSIE 6.0";
    private final static String QQ = "QQBrowser";
    private final static String GREEN = "GreenBrowser";
    private final static String SE360 = "360SE";
    private final static String FIREFOX = "Firefox";
    private final static String OPERA = "Opera";
    private final static String CHROME = "Chrome";
    private final static String SAFARI = "Safari";
    private final static String OTHER = "其它";

    /**
     * 判断是否是IE
     *
     * @param request
     * @return
     */
    public static boolean isIE(HttpServletRequest request) {
        return request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0 ||
                request.getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0") > 0;
    }

    /**
     * 获取IE版本
     *
     * @param request
     * @return
     */
    public static Double getIEVersion(HttpServletRequest request) {
        Double version = 0.0;
        if (getBrowserType(request, IE11)) {
            version = 11.0;
        } else if (getBrowserType(request, IE10)) {
            version = 10.0;
        } else if (getBrowserType(request, IE9)) {
            version = 9.0;
        } else if (getBrowserType(request, IE8)) {
            version = 8.0;
        } else if (getBrowserType(request, IE7)) {
            version = 7.0;
        } else if (getBrowserType(request, IE6)) {
            version = 6.0;
        }
        return version;
    }

    /**
     * 获取浏览器类型
     *
     * @param request
     * @return
     */
    public static String getBrowserType(HttpServletRequest request) {
        String browserType = OTHER;
        if (getBrowserType(request, IE11)) {
            browserType = BrowserType.IE11.name();
        }
        if (getBrowserType(request, IE10)) {
            browserType = BrowserType.IE10.name();
        }
        if (getBrowserType(request, IE9)) {
            browserType = BrowserType.IE9.name();
        }
        if (getBrowserType(request, IE8)) {
            browserType = BrowserType.IE8.name();
        }
        if (getBrowserType(request, IE7)) {
            browserType = BrowserType.IE7.name();
        }
        if (getBrowserType(request, IE6)) {
            browserType = BrowserType.IE6.name();
        }
        if (getBrowserType(request, FIREFOX)) {
            browserType = BrowserType.Firefox.name();
        }
        if (getBrowserType(request, SAFARI)) {
            browserType = BrowserType.Safari.name();
        }
        if (getBrowserType(request, CHROME)) {
            browserType = BrowserType.Chrome.name();
        }
        if (getBrowserType(request, OPERA)) {
            browserType = BrowserType.Opera.name();
        }
        if (getBrowserType(request, QQ)) {
            browserType = BrowserType.QQBrowser.name();
        }
        if (getBrowserType(request, GREEN)) {
            browserType = BrowserType.GreenBrowser.name();
        }
        if (getBrowserType(request, SE360)) {
            browserType = BrowserType.SE360.name();
        }
        return browserType;
    }

    private static boolean getBrowserType(HttpServletRequest request, String browserType) {
        return request.getHeader("USER-AGENT").indexOf(browserType) > 0;
    }
}
