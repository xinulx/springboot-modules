package com.springboot.common.util;

import com.springboot.common.business.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 解析参数工具类 <br/>
 */
public class DocumentUtil {
    // 日志
    private static final Logger logger = LoggerFactory.getLogger(DocumentUtil.class);

    /**
     * 解析参数，针对相同参数做缓存处理
     */
    public static Map<String, String> parseText(String param) {
        Map<String, String> map = new HashMap<String, String>();// 参数map
        // 判断空
        if (StringUtils.isEmpty(param)) {
            return map;
        }
        try {
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Regex " + param.replaceAll("&", "&amp;") + "></Regex>";
            Document document = DocumentHelper.parseText(xml);
            Element element = document.getRootElement();
            Iterator<?> it = element.attributeIterator();
            while (it.hasNext()) {
                Attribute attr = (Attribute) it.next();
                map.put(attr.getName(), attr.getValue());
            }
            return map;
        } catch (Throwable e) {
            logger.error("参数[" + param + "]解析错误.", e);
            throw new CommonException("参数[" + param + "]解析错误." + e.getMessage());
        }
    }
}