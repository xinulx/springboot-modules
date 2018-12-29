package com.springboot.tag.common.conf;

import com.springboot.common.busi.SpringContextHolder;
import com.springboot.common.util.PropertiesUtil;
import com.springboot.common.db.JdbcUtil;
import com.springboot.tag.common.impl.LabelServiceImpl;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class MyFreeMarkerConfigurer extends FreeMarkerConfigurer {

    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        JdbcUtil util = null;
        String url = PropertiesUtil.getProperty("spring.datasource.url");
        String username = PropertiesUtil.getProperty("spring.datasource.username");
        String password = PropertiesUtil.getProperty("spring.datasource.password");
        try {
            util = new JdbcUtil(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> eos = util.getJdbcTemplate().queryForList(
                "select id as labelId,label_name as labelName " +
                        "from sys_label where record_status = 'Normal' and label_type = '1'");
        if (eos == null || eos.size() == 0) {
            log.error("注入标签失败，无法获取标签数据！");
        }
        Map model = new HashMap();
        LabelServiceImpl labelServiceModel = SpringContextHolder.getBean("labelServiceModel");
        for (Map<String, Object> eo : eos) {
            log.info("注入自定义标签：" + eo.get("labelName"));
            model.put(eo.get("labelName").toString(), labelServiceModel);
        }
        model.put("includeX", SpringContextHolder.getBean("includeX"));  // include
        model.put("xml_escape", SpringContextHolder.getBean("fmXmlEscape"));  // xml解析
        Properties properties = new Properties();
        properties.load(new FileInputStream(System.getProperty("user.dir")
                + "/src/main/resources/freemarkerSettings.properties"));

        super.setFreemarkerVariables(model);
        super.setTemplateLoaderPath("/WEB-INF/freemarker/");
        super.setDefaultEncoding("UTF-8");
        super.setFreemarkerSettings(properties);

        super.afterPropertiesSet();
    }

}
