package com.springboot.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
	private static Logger logger = LoggerFactory.getLogger(IpUtil.class);
	private static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		String s = null;
		if (!(o instanceof String)) {
			s = o.toString();
		} else {
			s = (String) o;
		}

		if (s == null || s.trim().length() == 0 || "null".equals(s)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取登录用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		logger.info("userAgent >>>"+userAgent);
		String realIp="";
		if (!isEmpty(userAgent)) {
			String[] arr = userAgent.split(";");
			if(arr!=null && arr.length>0){
				for(String s:arr){
					if(!isEmpty(s) && s.indexOf("IP")>=0){
						String[] ss = s.split("=");
						if(ss.length==2){
							realIp = ss[1];
						}
					}
				}
			}
		}

		if(isEmpty(realIp)){
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				realIp = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				realIp = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				realIp = request.getRemoteAddr();
			}
			if (!isEmpty(ip) && ip.equals("0:0:0:0:0:0:0:1")) {
				realIp = "127.0.0.1";
			}
		}

		if(!isEmpty(realIp)) realIp = realIp.trim();

		return realIp;
	}

}
