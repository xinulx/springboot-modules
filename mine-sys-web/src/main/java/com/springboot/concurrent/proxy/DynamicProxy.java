package com.springboot.concurrent.proxy;

import com.springboot.concurrent.inter.Subject;
import com.springboot.concurrent.service.RealSubject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理类，实现InvocationHandler接口，并重写该invoke方法
 */
public class DynamicProxy implements InvocationHandler {
    private Object object;
    public DynamicProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Other things
        Object result = method.invoke(object, args);
        return result;
    }
    public static void main(String[] args){
        Subject realSubject = new RealSubject();
        DynamicProxy proxy = new DynamicProxy(realSubject);
        ClassLoader classLoader = realSubject.getClass().getClassLoader();
        Subject subject = (Subject) Proxy.newProxyInstance(classLoader, new  Class[]{Subject.class}, proxy);
        String result = subject.execute("dqwdqw");
        System.out.println(result);
    }
}