package com.springboot.concurrent.service;

import com.springboot.concurrent.inter.Subject;

public class RealSubject implements Subject {

    @Override
    public <T> T execute(Object o) {
        return (T) o;
    }
}