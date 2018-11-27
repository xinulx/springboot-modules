package com.springboot.controller;

import com.alibaba.fastjson.JSONArray;
import com.springboot.cache.redis.RedisUtil;
import com.springboot.common.filter.ShiroUtil;
import com.springboot.vo.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("cache")
public class CacheController {

    @RequestMapping("index")
    public String index(){
        return "/cache/index";
    }

    @RequestMapping("getPage")
    @ResponseBody
    public Object getPage(){
        Set<String> allKeys = RedisUtil.getAllKeys();
        List list = new ArrayList();
        for(String s:allKeys){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("cacheKey",s);
            list.add(item);
        }
        return list;
    }

    @RequestMapping("getCacheBySid")
    @ResponseBody
    public Object getCacheBySid(String key) {
        if (key == null) {
            return null;
        }
        // 序列化键
        byte[] keyByteArray = key.getBytes();
        // 从redis中获取数据
        byte[] valueByteArray = RedisUtil.get(keyByteArray);
        if(key.indexOf("EO") != -1){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("attributes",JSONArray.parse(valueByteArray));
            return map;
        }
        // 返回对应的数据
        return valueByteArray == null ? null : SerializationUtils.deserialize(valueByteArray);
    }

    @RequestMapping("delete")
    @ResponseBody
    public Object delete(String key) {
        if (key == null) {
            return null;
        }
        // 序列化键
        byte[] keyByteArray = key.getBytes();
        RedisUtil.del(keyByteArray);
        // 返回对应的数据
        return ResponseData.success();
    }

    @RequestMapping("deleteAll")
    @ResponseBody
    public Object delAll(){
        RedisUtil.delByPrefix(ShiroUtil.SHIRO_CACHE_PREFIX);
        return ResponseData.success();
    }
}
