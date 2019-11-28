package com.springboot.common.util;

import com.springboot.common.business.SpringContextHolder;
import com.springboot.entity.business.GrabConfigEO;
import com.springboot.service.hibernate.IGrabConfigService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网页数据抓取工具
 */
public class DataGrabUtils {

    private static IGrabConfigService grabConfigService = SpringContextHolder.getBean("grabConfigService");

    /**
     * 根据配置数据抓取信息
     *
     * @param id
     * @return
     */
    public static Object getGrabData(Long id) {
        List result = new ArrayList();
        GrabConfigEO eo = grabConfigService.getEntity(GrabConfigEO.class, id);
        if (eo == null) {
            return result;
        }
        try {
            Document doc = Jsoup.connect(eo.getGrabUrl()).timeout(5000).get();
            String filter = ":lt(" + eo.getNum() + ")";
            Elements list = doc.select(eo.getList() + filter);
            for (Element e : list) {
                Map map = new HashMap();
                String title = e.select(eo.getTitle()).text();
                String url = e.select(eo.getUrl()).attr("href");
                String date = e.select(eo.getDate()).text();
                if (!AppUtil.isEmpty(eo.getReplaceRule())) {
                    url = url.replace(eo.getReplaceRule(), eo.getReplaceValue());
                }
                if (!url.startsWith("http")) {
                    url = eo.getDomain().concat(url);
                }
                map.put("title", title);
                map.put("date", date);
                map.put("url", url);
                result.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
