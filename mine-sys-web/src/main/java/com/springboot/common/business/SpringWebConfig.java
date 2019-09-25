package com.springboot.common.business;

import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * web MVC 适配器 * 配置拦截器、处理静态资源、配置消息转换等
 */
@Component
public class SpringWebConfig extends WebMvcConfigurerAdapter {

    /**
     * 设置默认页面，类似于web.xml的welcome-page
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    /**
     * 初始化时创建实例
     * @return
     */
    @Bean
    public CommonInterceptor logInterceptor() {
        return new CommonInterceptor();
    }

    /**
     * 注册拦截器
     * @param registry 拦截器注册中心
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器里有@Autowire或者@Resource注入的属性就这么用
        registry.addInterceptor(logInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    /**
     * 静态资源处理
     * 将请求的如css、html等静态资源定位到项目指定位置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
    }
}
