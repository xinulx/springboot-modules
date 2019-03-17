package com.springboot.tag.generate;

import com.springboot.tag.common.AbstractBeanService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DemoBeanService extends AbstractBeanService {

    @Override
    public String objToStr() {
        Integer num = getParamObj().getInteger("num");
        String desc = getParamObj().getString("desc");
        Map root = new HashMap();
        root.put("num", num);
        root.put("desc", desc);
        String html = processHtml(root);
        html = html.toString().replaceAll("[\\n\\r]", "");
        return html;
    }
}
