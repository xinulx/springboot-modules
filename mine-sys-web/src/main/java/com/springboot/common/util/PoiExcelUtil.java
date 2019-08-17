package com.springboot.common.util;

public class PoiExcelUtil {

    /**
     * 后缀
     *
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            String suffix = fileName.substring(index + 1);
            return suffix;
        }
        return "";
    }
}
