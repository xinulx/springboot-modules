package com.springboot.concurrent.util;

import com.springboot.concurrent.inter.Subject;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 生成目标接口的代理类
 */
public class CodeUtil {
    public static void main(String[] args) throws IOException {
        byte[] classFile = ProxyGenerator.generateProxyClass("Subject", Subject.class.getInterfaces());
        File file = new File(System.getProperty("user.dir")+"/sys-web/doc/Subject.class");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(classFile);
        fos.flush();
        fos.close();
    }
}
