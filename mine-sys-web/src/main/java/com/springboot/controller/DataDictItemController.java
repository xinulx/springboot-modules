package com.springboot.controller;

import com.springboot.common.busi.RightDictCache;
import com.springboot.common.util.LoginPersonUtil;
import com.springboot.entity.business.datadictionary.DataDictEO;
import com.springboot.entity.business.datadictionary.DataDictItemEO;
import com.springboot.service.hibernate.IDataDictItemService;
import com.springboot.service.hibernate.IDataDictService;
import com.springboot.entity.vo.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: DataDictItemController
 * @Description: 数据字典项控制器
 */
@Controller
@RequestMapping(value = "dictItem", produces = {"application/json;charset=UTF-8"})
public class DataDictItemController {

    private Logger logger = LoggerFactory.getLogger(DataDictItemController.class);

    @Autowired
    private IDataDictItemService dataDictItemService;

    @Autowired
    private IDataDictService dataDictService;

    @ModelAttribute
    public void putAttr(ModelMap map) {
        map.put("rightsCode", LoginPersonUtil.isRoot() ? "root" : LoginPersonUtil.isSuperAdmin() ? "superAdmin" : LoginPersonUtil.isSiteAdmin() ? "siteAdmin"
                : "normal");
    }

    /**
     * @return
     * @Description 跳转字典项列表
     */
    @RequestMapping("dictItemList")
    public String dictItemList(Long dictId, ModelMap map) {
        map.put("dictId", dictId);
        return "/system/datadictionary/dictItem_List";
    }

    /**
     * @param pageIndex
     * @param pageSize
     * @param dictId
     * @return
     * @Description 根据字典ID获取字典项分页
     */
    @RequestMapping("getPage")
    @ResponseBody
    public Object getPage(Long pageIndex, Integer pageSize, Long dictId, String name) {
        if (pageIndex == null)
            pageIndex = 0L;
        if (pageSize == null)
            pageSize = 10;
        return dataDictItemService.getPageByDictId(pageIndex, pageSize, dictId, name);
    }

    /**
     * @param dictId
     * @return
     * @Description 根据字典ID获取字典项集合
     */
    @RequestMapping("getListByDictId")
    @ResponseBody
    public Object getListByDictId(Long dictId) {
        return ResponseData.success(dataDictItemService.getListByDictId(dictId));
    }

    /**
     * @param dictId
     * @param map
     * @return
     * @Description 新增页面
     */
    @RequestMapping("addItem")
    public String addItem(Long dictId, ModelMap map) {
        map.put("DataDict", dataDictService.getEntity(DataDictEO.class, dictId));
        map.put("maxSort", dataDictItemService.getMaxItem(dictId) + 2);
        return "/system/datadictionary/addItem";
    }

    /**
     * @param dictItem
     * @return
     * @Description 保存增加
     */
    @RequestMapping("saveItem")
    @ResponseBody
    public Object saveItem(DataDictItemEO dictItem) {
        DataDictItemEO item = dataDictItemService.getDataDictItemByCode(dictItem.getCode(), dictItem.getDataDicId());
        if (item != null) {
            return ResponseData.fail("唯一编码已存在！");
        }
        if (LoginPersonUtil.isRoot()) {
            dictItem.setSiteId(null);
        } else if (LoginPersonUtil.isSiteAdmin() || LoginPersonUtil.isSuperAdmin()) {
            dictItem.setSiteId(LoginPersonUtil.getSiteId());
        } else {
            ResponseData.fail("您无此权限");
        }
        // 更新数据字典
        return ResponseData.success(dataDictItemService.saveItem(dictItem));
    }

    /**
     * @param ids
     * @return
     * @Description 删除字典项
     */
    @RequestMapping("deleteItem")
    @ResponseBody
    public Object deleteItem(@RequestParam("ids[]") Long[] ids) {
        dataDictItemService.delete(DataDictItemEO.class, ids);
        if (null != ids && ids.length > 0) {
            for (Long id : ids) {
                // 删除数据字典
                dataDictItemService.delete(DataDictItemEO.class, id);
            }
        }
        RightDictCache.refresh();
        return ResponseData.success();
    }

    /**
     * @param itemId
     * @param map
     * @return
     * @Description 编辑字典页
     */
    @RequestMapping("editItem")
    public String editItem(Long itemId, ModelMap map) {
        DataDictItemEO itemEO = dataDictItemService.getEntity(DataDictItemEO.class, itemId);
        map.put("ItemEO", itemEO);
        if(itemEO != null){
            map.put("DataDict", dataDictService.getEntity(DataDictEO.class, itemEO.getDataDicId()));
        }
        return "/system/datadictionary/editItem";
    }

    /**
     * 修改隐藏状态
     *
     * @param itemId
     * @return
     */
    @RequestMapping("setHide")
    @ResponseBody
    public Object setHide(Long itemId, Integer flag) {
        dataDictItemService.updateHide(itemId, flag);
        return ResponseData.success();
    }

    /**
     * @param itemEO
     * @return
     * @Description 保存修改
     */
    @RequestMapping("updateItem")
    @ResponseBody
    public Object updateItem(DataDictItemEO itemEO) {
        if (LoginPersonUtil.isRoot()) {
            itemEO.setSiteId(null);
        } else if (LoginPersonUtil.isSiteAdmin() || LoginPersonUtil.isSuperAdmin()) {
            itemEO.setSiteId(LoginPersonUtil.getSiteId());
        } else {
            ResponseData.fail("您无此权限");
        }
        // 更新数据字典
        dataDictItemService.updateItem(itemEO);
        return ResponseData.success();
    }

    /**
     * @param itemId
     * @param dictId
     * @param flag
     * @return Object return type
     * @throws
     * @Title: setDefault
     * @Description: 设置默认值
     */
    @RequestMapping("setDefault")
    @ResponseBody
    public Object setDefault(Long itemId, Long dictId, Integer flag) {
        dataDictItemService.setDefault(itemId, dictId, flag);
        return ResponseData.success();
    }

}
