package com.springboot.tag.common;

import com.alibaba.fastjson.JSONObject;
import com.springboot.common.business.CommonException;
import com.springboot.common.business.SpringContextHolder;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用正则表达式匹配标签抽象类
 */
@Slf4j
public abstract class AbstractBeanService {

    private static FreeMarkerConfigurer configurer = SpringContextHolder.getBean(FreeMarkerConfigurer.class);

    private Template template;

    private String templatePath;

    private JSONObject paramObj;

    public void setParamObj(JSONObject paramObj) {
        this.paramObj = paramObj;
    }

    public JSONObject getParamObj() {
        return paramObj;
    }

    public String getTemplatePath() {
        log.info("标签参数：{}", paramObj);
        if (paramObj.getString("file") == null) {
            this.templatePath = paramObj.getString("labelName");
        } else {
            this.templatePath = paramObj.getString("file");
        }
        log.info("模板路径={}",templatePath);
        return templatePath;
    }

    /**
     * 将查询结果转换为字符串
     * 沒有实现时则直接返回参数
     * @return String
     */
    public abstract String objToStr();

    /**
     * 获取标签模板
     *
     * @param templateName
     * @return Template
     */
    protected Template setTemplate(String templateName) {
        try {
            this.template = configurer.getConfiguration().getTemplate(templateName);
            return template;
        } catch (IOException e) {
            throw new CommonException("获取模板出错：" + e.getMessage());
        }
    }

    /**
     * 解析模板生成html
     *
     * @return
     */
    protected String processHtml(Object obj) {
        setTemplate(getTemplatePath());
        Writer out = new StringWriter();
        try {
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("resultObj",obj);
            resultMap.put("paramObj",paramObj);
            template.process(resultMap, out);
            return out.toString();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
