package com.springboot.common.business;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
public class CommonInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 将handler强转为HandlerMethod
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 从方法处理器中获取出要调用的方法
        Method method = handlerMethod.getMethod();
        // 获取出方法上的Access注解
        CommonLogParams systemLogAnnotation = method.getAnnotation(CommonLogParams.class);
        if (systemLogAnnotation == null) {
            // 如果注解为null, 说明不需要拦截, 直接放过
            return true;
        }
        if (systemLogAnnotation != null) {
            // 如果自定义注解不为空, 则取出配置值
            log.info("***********************用户登陆日志记录*************************");
            String clazz = systemLogAnnotation.value().getName();
            String type = systemLogAnnotation.type();
            String busiMethod = systemLogAnnotation.method();
            String desc = systemLogAnnotation.desc();
            log.info(clazz);
            log.info(type);
            log.info(busiMethod);
            log.info(desc);

            // 方法参数
            Map<String, String[]> paramsMap = arg0.getParameterMap();
            log.info("请求参数：{}", JSON.toJSONString(paramsMap.values()));
            // 保存操作日志
            return true;
        }
        return false;
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) {

    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) {
        int status = arg1.getStatus();
        String viewName;
        if (status == 200) {
            return;
        } else if (status == 403) {
            viewName = "/pages/error/403";
        } else if (status == 404) {
            viewName = "/pages/error/404";
        } else if (status == 500) {
            viewName = "/pages/error/500";
        } else if (status == 503) {
            viewName = "/pages/error/503";
        } else {
            viewName = "/pages/error/unknow";
        }
        if(arg3 != null){
            arg3.setViewName(viewName);
        }
    }
}
