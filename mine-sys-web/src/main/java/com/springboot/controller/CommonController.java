package com.springboot.controller;

import com.springboot.activemq.producer.Producer;
import com.springboot.entity.vo.ResponseData;
import com.springboot.service.common.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/common")
public class CommonController {

    @Resource
    private ICommonService commonService;

    @Autowired
    private Producer producer;

    @RequestMapping("/getConstant")
    @ResponseBody
    public ResponseData getConstantList(String tableName, String columnName) {
        Map<String, Object> params = new HashMap<>();
        params.put("tableName", tableName);
        params.put("columnName", columnName);
        List<Map<String, Object>> constantList = commonService.getConstantList(params);
        return ResponseData.success(constantList, "查询成功！");
    }

    @RequestMapping(value = "/{errorCode}", method = RequestMethod.GET)
    public String errorPage(@PathVariable("errorCode") String errorCode) {
        return "/pages/error/" + errorCode;
    }

    @RequestMapping(value = "/activeMQ/process")
    @ResponseBody
    public boolean generalStatic(String message) {
        producer.sendMessage(message);
        return Boolean.TRUE;
    }
}
