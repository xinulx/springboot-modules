package com.springboot.tag.common;

import com.alibaba.fastjson.JSONObject;
import com.springboot.entity.mongo.ContentMongoEO;
import com.springboot.service.mongodb.impl.MongoServiceImpl;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateModel;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

@Component("includeX")
public class IncludeXMacro implements TemplateDirectiveModel {

    private static final String PATH_PARAM = "id";

    @Autowired
    private MongoServiceImpl mongoService;

    @Override
    public void execute(Environment environment, Map params, TemplateModel[] templateModel,
                        TemplateDirectiveBody directiveBody) throws IOException {
        JSONObject paramObj = new JSONObject(params);
        Long id = Long.valueOf(paramObj.getString(PATH_PARAM));
        ContentMongoEO mongoEO = mongoService.queryById(id == null?0:id);
        String content;
        if (mongoEO == null) {
            content = "";
        } else {
            content = URLDecoder.decode(Base64.decodeToString(mongoEO.getContent()), "UTF-8");
        }
        environment.getOut().write(content);
    }
}