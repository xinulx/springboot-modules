package com.springboot.concurrent.inter;

/**
 * 代理接口:委托类
 */
public interface Subject {

    <T> T execute(Object o);
}
