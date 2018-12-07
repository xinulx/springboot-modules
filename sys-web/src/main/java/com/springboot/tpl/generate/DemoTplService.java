package com.springboot.tpl.generate;

import com.springboot.tpl.service.AbstractTplService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DemoTplService extends AbstractTplService {

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
