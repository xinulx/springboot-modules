package com.springboot.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
    private static Logger logger = LoggerFactory.getLogger(IpUtil.class);

    /**
     * 获取登录用户IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        logger.info("userAgent >>>" + userAgent);
        String realIp = "";
        if (!AppUtil.isEmpty(userAgent)) {
            String[] arr = userAgent.split(";");
            if (arr != null && arr.length > 0) {
                for (String s : arr) {
                    if (!AppUtil.isEmpty(s) && s.indexOf("IP") >= 0) {
                        String[] ss = s.split("=");
                        if (ss.length == 2) {
                            realIp = ss[1];
                        }
                    }
                }
            }
        }

        if (AppUtil.isEmpty(realIp)) {
            String ip = request.getHeader("x-forwarded-for");
            if (isUnKnow(ip)) {
                realIp = request.getHeader("Proxy-Client-IP");
            }
            if (isUnKnow(ip)) {
                realIp = request.getHeader("WL-Proxy-Client-IP");
            }
            if (isUnKnow(ip)) {
                realIp = request.getRemoteAddr();
            }
            if (!AppUtil.isEmpty(ip) && ip.equals("0:0:0:0:0:0:0:1")) {
                realIp = "127.0.0.1";
            }
        }

        if (!AppUtil.isEmpty(realIp)) realIp = realIp.trim();

        return realIp;
    }

    private static boolean isUnKnow(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

}
