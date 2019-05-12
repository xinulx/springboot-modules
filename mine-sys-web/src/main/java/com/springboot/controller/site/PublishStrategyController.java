package com.springboot.controller.site;

import com.alibaba.fastjson.JSON;
import com.springboot.common.util.AjaxObj;
import com.springboot.controller.BaseController;
import com.springboot.entity.business.LimitMappingEO;
import com.springboot.entity.business.PublishStrategyEO;
import com.springboot.entity.vo.QueryVO;
import com.springboot.service.hibernate.ILimitMappingService;
import com.springboot.service.hibernate.IPublishStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auth wangshibao
 * @createDate 2019/4/24 11:47
 */
@Controller
@RequestMapping("/publishStrategy")
public class PublishStrategyController extends BaseController {

    private static final String FILE_BASE = "/system/publishstrategy/";

    @Autowired
    private IPublishStrategyService publishStrategyService;

    @Autowired
    private ILimitMappingService limitMappingService;

    @RequestMapping("/index")
    public String index() {
        return FILE_BASE + "/index";
    }

    /**
     * 保存
     *
     * @param eo
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveStrategyEO")
    public Object add(PublishStrategyEO eo) {
        eo.setSiteId(4697351L);
        eo = this.publishStrategyService.saveOrUpdateConfigEO(eo);
        return AjaxObj.Ok(eo);
    }

    /**
     * 获取发布策略
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getStrategyEOById")
    public Object getConfigEOByTemplateId() {
        Object eo = this.publishStrategyService.getEOBySiteId(4697351L);
        return AjaxObj.Ok(eo);
    }

    @ResponseBody
    @RequestMapping("/getPage")
    public Object getPage(QueryVO vo) {
        return limitMappingService.getPage(vo);
    }

    @Autowired
    WebApplicationContext applicationContext;

    @RequestMapping(value = "v1/getAllUrl")
    @ResponseBody
    public Object getAllUrl() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            Map<String, String> map1 = new HashMap<String, String>();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String url : p.getPatterns()) {
                map1.put("url", url);
            }
            map1.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
            map1.put("method", method.getMethod().getName()); // 方法名
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                map1.put("type", requestMethod.toString());
            }
            list.add(map1);
            generateMapping(map1);
        }
        return AjaxObj.Ok(JSON.toJSON(list));
    }

    private void generateMapping(Map<String, String> map) {
        Long count = limitMappingService.getCountByUrl(map.get("url"), null);
        if (count > 0) {
            return;
        }
        LimitMappingEO eo = new LimitMappingEO();
        eo.setSiteId(4697351L);
        eo.setUrl(map.get("url"));
        eo.setClassName(map.get("className"));
        eo.setMethodName(map.get("method"));
        eo.setType(map.get("type") == null ? "all" : map.get("type"));
        limitMappingService.saveEntity(eo);
    }

    @RequestMapping("batchUpdate")
    @ResponseBody
    public Object updateStatus(@RequestParam(value = "ids[]") Long[] ids, Integer status) {
        if (ids == null || ids.length == 0) {
            return AjaxObj.Err("请选择受限请求");
        }
        for (Long id : ids) {
            limitMappingService.updateStatus(id, status);
        }
        return AjaxObj.Ok();
    }
}
