package com.springboot.concurrent.proxy;

import com.springboot.concurrent.inter.Subject;
import com.springboot.concurrent.service.RealSubject;

/**
 * 静态代理实现
 */
public class ProxySubject implements Subject {

    private Subject subject;

    public ProxySubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public <T> T execute(Object o) {
        // TODO Other things
        return subject.execute(o);
    }
    
    public static void main(String[] args){
        ProxySubject proxySubject = new ProxySubject(new RealSubject());
        String result = proxySubject.execute("212121");
        System.out.println(result);
    }
}