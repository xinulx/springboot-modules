package com.springboot.controller;

import com.springboot.common.business.CommonDictCache;
import com.springboot.common.util.AppUtil;
import com.springboot.common.util.LoginPersonUtil;
import com.springboot.entity.business.DataDictEO;
import com.springboot.service.hibernate.IDataDictService;
import com.springboot.entity.vo.DataDictPageVO;
import com.springboot.entity.vo.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 数据字典控制器
 */
@Controller
@RequestMapping(value = "dataDict", produces = {"application/json;charset=UTF-8"})
public class DataDictController {

    @Autowired
    private IDataDictService dataDictService;

    /**
     * @return
     * @Description 跳转数据字典分页列表
     */
    @RequestMapping("datadict_list")
    public String dataDictList(ModelMap map) {
        map.put("isRoot", LoginPersonUtil.isRoot());
        return "/system/datadictionary/datadict_list";
    }

    /**
     * @param pageVO
     * @return
     * @Description 按条件获取数据字典分页
     */
    @RequestMapping("getPage")
    @ResponseBody
    public Object getPage(DataDictPageVO pageVO, String dataFlag) {
        return dataDictService.getPage(pageVO);
    }

    /**
     * @return
     * @Description 跳转添加数据字典页
     */
    @RequestMapping("addDict")
    public String addDict() {
        return "/system/datadictionary/addDict";
    }

    /**
     * @param dataDictEO
     * @return
     * @Description 保存字典
     */
    @RequestMapping("saveDict")
    @ResponseBody
    public Object saveDict(DataDictEO dataDictEO) {
        if (!LoginPersonUtil.isRoot()) {
            dataDictEO.setSiteId(null);
        } else {
            return ResponseData.fail("您无此权限");
        }
        if (AppUtil.isEmpty(dataDictEO.getCode())) {
            return ResponseData.fail("唯一编码不能为空！");
        }
        DataDictEO dataDict = dataDictService.getDataDictByCode(dataDictEO.getCode());
        if (dataDict != null) {
            return ResponseData.fail("唯一编码已存在！");
        }
        // 更新数据字典
        return ResponseData.success(dataDictService.saveEntity(dataDictEO));
    }

    /**
     * @param dictId
     * @param model
     * @return
     * @Description 跳转编辑页面
     */
    @RequestMapping("editDict")
    public String editDict(Long dictId, ModelMap model) {
        model.put("DictEO", dataDictService.getEntity(DataDictEO.class, dictId));
        return "/system/datadictionary/editDict";
    }

    /**
     * @param dataDictEO
     * @return
     * @Description 保存修改
     */
    @RequestMapping("updateDict")
    @ResponseBody
    public Object updateDict(DataDictEO dataDictEO) {
        if (!LoginPersonUtil.isRoot()) {
            dataDictEO.setSiteId(null);
        } else {
            return ResponseData.fail("您无此权限");
        }
        dataDictService.updateEntity(dataDictEO);
        // 更新数据字典
        dataDictService.saveOrUpdateEntity(dataDictEO);
        return ResponseData.success();
    }

    /**
     * @param id
     * @return
     * @Description 删除字典及字典项
     */
    @RequestMapping("deleteDict")
    @ResponseBody
    public Object deleteDict(Long id) {
        if (LoginPersonUtil.isRoot()) {
            // 删除数据字典
            dataDictService.deleteDict(id);
            CommonDictCache.refresh();
            return ResponseData.success();
        } else {
            return ResponseData.fail("您无此权限");
        }

    }

    /**
     * @param id
     * @return Object return type
     * @throws
     * @Title: resetUse
     * @Description: 设置使用情况
     */
    @RequestMapping("resetUse")
    @ResponseBody
    public Object resetUse(Long id) {
        if (LoginPersonUtil.isRoot()) {
            dataDictService.changeUsed(id);
            return ResponseData.success();
        } else {
            return ResponseData.fail("您无此权限");
        }
    }
}
