package com.springboot.common.util;

public class Screen {
    /**
     * 如果出现异常请在VM中添加：-Djava.awt.headless=false
     * @return
     */
    public static String getScreen() {
        int screenWidth = ((int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().width);
        int screenHeight = ((int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);
        System.out.println("屏幕分辨率：" + screenWidth + " * " + screenHeight);
        return screenWidth + " * " + screenHeight;
    }
}