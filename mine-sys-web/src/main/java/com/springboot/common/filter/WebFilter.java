package com.springboot.common.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.filters.RemoteIpFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 这是一个置自定义的filter
 * 添加多视图解析，按优先级查找，直到找到为止
 */
@Configuration
@ImportResource("applicationContext.xml")
@Slf4j
public class WebFilter {
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("webFilter");
        registration.setOrder(1);
        return registration;
    }

    public class MyFilter implements Filter {
        @Override
        public void destroy() {
        }

        @Override
        public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
                throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest) srequest;
            String url = request.getRequestURI();
            if(!StringUtils.isEmpty(url) && !url.contains("css") && !url.contains("js")
                    && !url.contains("images") && !url.contains("plugins") && !url.contains("favicon")){
                log.info("current request url= {}" ,request.getRequestURI());
                log.info("current request param = {}" , JSON.toJSONString(srequest.getParameterMap()));
            }
            filterChain.doFilter(srequest, sresponse);
        }

        @Override
        public void init(FilterConfig arg0) {
            log.info("load init param " + arg0.getInitParameterNames());
        }
    }
}