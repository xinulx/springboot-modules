package com.springboot.tag.common;

import com.alibaba.fastjson.JSONObject;
import com.springboot.common.business.CommonException;
import com.springboot.common.business.SpringContextHolder;
import com.springboot.common.util.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlRegUtils {

    // 该正则表达式支持两种闭合方式、支持标签嵌套、支持取单独属性，暂不支持相同标签嵌套
    private static final String LIST_REGEX = "\\{mine:([\\w]+)([^\\}]*)(?:/\\}|\\}(.*?)\\{/mine:\\1\\})";

    // 两种模式，为了应对换行的匹配
    private static final Pattern LIST_PATTERN = Pattern.compile(LIST_REGEX, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    // 属性解析
    private static final String ATTR_REGEX = "\\[mine:([\\w]+)([^\\]]*)\\]";
    // 只区分大小写
    public static final Pattern ATTR_PATTERN = Pattern.compile(ATTR_REGEX, Pattern.CASE_INSENSITIVE);

    /**
     * 标签体 ADD REASON. <br/>
     */
    private static class LabelEntry {
        private String labelName;
        private String param;
        private JSONObject object;

        public LabelEntry(String labelName, String param, JSONObject object) {
            super();
            this.labelName = labelName;
            this.param = param;
            this.object = object;
        }

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public JSONObject getMap() {
            return object;
        }

        public void setMap(JSONObject object) {
            this.object = object;
        }
    }

    public static String parseLabel(String content) {

        int index = 0;
        // 结果
        StringBuffer sb = new StringBuffer();
        // 合并标签解析
        List<LabelEntry> labelEntryList = new ArrayList<LabelEntry>();
        // 正则匹配，当解析指定标签时， 不预处理文章类型，防止文章标题被替换
        Matcher m = LIST_PATTERN.matcher(content);
        // 查找
        while (m.find()) {
            // 标签名称
            String labelName = m.group(1).trim();
            // 参数
            String param = m.group(2).trim();
            // 放入list中
            JSONObject object = new JSONObject();
            object.put("labelName", labelName);
            String[] params = param.split(" ");
            for (String s : params) {
                if (s.equals("") || s.trim().equals("")) {
                    continue;
                }
                String[] itemParam = s.split("=");
                object.put(itemParam[0].replaceAll(" ", ""), processStr(itemParam[1].replaceAll(" ", "")));
            }
            labelEntryList.add(new LabelEntry(labelName, param, object));
            // 替换
            m.appendReplacement(sb, "\\{" + labelName + ":" + index++ + "\\}");
        }

        m.appendTail(sb);
        //简化后的字符串模板，附带索引
        String result = sb.toString();
        int pos = 0;
        for (LabelEntry entry : labelEntryList) {
            AbstractBeanService tplService = SpringContextHolder.getBean(entry.labelName + "BeanService");
            tplService.setParamObj(entry.object);
            String labelResult = tplService.objToStr();
            result = result.replace("{" + entry.labelName + ":" + pos + "}", labelResult);
            pos++;
        }
        return result;
    }

    /**
     * 解析属性，并对属性进行预处理操作
     */
    public static String parseProperty(String content, Object obj) {
        // 结果
        StringBuffer buffer = new StringBuffer();
        // 正则匹配
        Matcher m = ATTR_PATTERN.matcher(content);
        // 查找
        while (m.find()) {
            String key = m.group(1).trim();// 字段
            String param = m.group(2).trim();// 参数
            // 参数map
            Map<String, String> paramMap = parseText(param);
            System.out.println("属性：" + key + "参数：" + paramMap);
            // 预处理字段值
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                try {

                } catch (Throwable e) {
                    throw new CommonException("预处理[" + entry.getKey() + "]后台处理逻辑不存在." + e.getMessage());
                }
            }
            // 由于$出现在replacement中时，表示对捕获组的反向引用，所以要对上面替换内容中的$进行替换
            m.appendReplacement(buffer, Matcher.quoteReplacement(obj.toString()));
        }
        m.appendTail(buffer);
        return buffer.toString();
    }

    public static void main(String[] args) {
        String content = "测试标签<div>[mine:title length=\"30\"]\n{mine:aa num=1 name=test}{/mine:aa}{mine:bb num=1 name=test}{/mine:bb}{mine:cc a=1 b=2/}</div>";
        System.out.println(parseLabel(content));
        System.out.println(parseProperty(content, "211221"));
    }

    public static String processStr(String str) {
        if (AppUtil.isEmpty(str)) {
            return "";
        }
        if (str.length() >= 2) {
            return str.substring(1, str.length() - 1);
        }
        return "";
    }

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
            System.err.println("参数[" + param + "]解析错误.\n" + e.getMessage());
            throw new CommonException("参数[" + param + "]解析错误." + e.getMessage());
        }
    }
}
