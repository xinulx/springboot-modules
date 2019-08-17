package com.springboot.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

@Slf4j
public class PropertiesUtil {
    public static String getProperty(String key) {
        log.info("*********************************读取配置属性start********************************");
        Properties prop = new Properties();
        try {
            //读取属性文件a.properties
            String dir = System.getProperty("user.dir");
            InputStream in = new BufferedInputStream(new FileInputStream(dir + "/src/main/resources/application.properties"));
            prop.load(in);     ///加载属性列表
            Iterator<String> it = prop.stringPropertyNames().iterator();
            while (it.hasNext()) {
                String tmpKey = it.next();
                if (tmpKey.equals(key)) {
                    log.warn(key + ":" + prop.getProperty(key));
                    in.close();
                    return prop.getProperty(key);
                }
            }
            ///保存属性到b.properties文件
            FileOutputStream oFile = new FileOutputStream("tmpSimilar.properties", true);//true表示追加打开
            prop.setProperty("date", DateUtil.getStrToday());
            prop.store(oFile, "The New properties file");
            oFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        log.info("*********************************读取配置属性end********************************");
        return null;
    }
}