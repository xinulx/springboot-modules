package com.springboot.common.business;

import javafx.application.Application;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 启动springboot项目的方式二，一般在集成的时候会使用
 * 覆盖configure()，把启动类Application注册进去
 * 外部web应用服务器构建Web Application Context的时候，会把启动类添加进去
 */
public class SpringServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}