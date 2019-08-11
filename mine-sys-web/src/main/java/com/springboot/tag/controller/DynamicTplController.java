package com.springboot.tag.controller;

import com.springboot.common.business.SpringContextHolder;
import com.springboot.common.util.AppUtil;
import com.springboot.dao.mongo.impl.ContentMongoDaoImpl;
import com.springboot.entity.mongo.ContentMongoEO;
import com.springboot.tag.common.HtmlRegUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLDecoder;

/**
 * 解析字符串模板
 */
@Controller
@RequestMapping("/site")
public class DynamicTplController {

    @Autowired
    private ContentMongoDaoImpl contentMongoDao;

    @RequestMapping(value = "/tpl/{id:[\\d\\.]+}", produces = "text/html;charset=UTF-8")
    public String getHtml(@PathVariable long id, Model model,
                          HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        ContentMongoEO contentMongoEO = contentMongoDao.queryById(id);
        if (contentMongoEO == null || AppUtil.isEmpty(contentMongoEO.getContent())) {
            model.addAttribute("tplContent", "模板内容为空");
            return "/design/preview";
        }
        // 解码中文、解密
        String tplContent = URLDecoder.decode(Base64.decodeToString(contentMongoEO.getContent()), "UTF-8");
        // 解析自定义标签
        tplContent = HtmlRegUtils.parseLabel(tplContent);
        // 将模板写入文件
        // FileUtils.write(new File(PropertiesUtil.getProperty("fm.tpl.path")), tplContent);

        // 解析freemarker扩展标签
        FreeMarkerConfigurer configurer = SpringContextHolder.getBean(FreeMarkerConfigurer.class);
        Configuration configuration = configurer.getConfiguration();
        configuration.clearTemplateCache();
        StringWriter out = new StringWriter();
        new Template("template", new StringReader(tplContent), configuration).process(model, out);
        // 设置模板内容
        model.addAttribute("tplContent",out.toString());
        return "/design/preview";
    }
}
