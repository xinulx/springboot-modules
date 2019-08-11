package com.springboot.common.util;

import com.springboot.common.business.CommonException;
import com.springboot.entity.business.DataDictEO;
import com.springboot.entity.business.DataDictItemEO;
import com.springboot.service.hibernate.IDataDictItemService;
import com.springboot.service.hibernate.IDataDictService;
import com.springboot.entity.vo.DataDictVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataDictionaryUtil {

    @Autowired
    private static IDataDictService dataDictService;
    @Autowired
    private static IDataDictItemService dataDictItemService;

    /**
     * 根据数据字典code获取数据字段项列表
     */
    private static List<DataDictItemEO> getDictItemList(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        DataDictEO ddeo = dataDictService.getEntity(DataDictEO.class, map);
        if (null == ddeo) {
            throw new CommonException(CommonException.TipsMode.Message.toString(), "数据字典数据不存在！");
        }

        return dataDictItemService.getItemList(ddeo.getDictId());
    }

    /**
     * 根据数据字典code获取数据字段项列表
     *
     * @param code
     */
    public static List<DataDictVO> getDDList(String code) {
        List<DataDictItemEO> itemList = getDictItemList(code);
        if (null == itemList || itemList.isEmpty()) {
            return Collections.emptyList();
        }
        List<DataDictVO> list = new ArrayList<DataDictVO>();
        for (DataDictItemEO eo : itemList) {
            if (eo.getIsHide() == 1) {
                continue;
            }
            DataDictVO vo = new DataDictVO();
            vo.setName(eo.getName());
            vo.setKey(eo.getName());
            vo.setValue(eo.getValue());
            vo.setCode(eo.getCode());
            vo.setDefault(eo.getIsDefault() == 1);
            list.add(vo);
        }
        return list;
    }

    /**
     * 根据编码 code 和站点 siteid 获取数据字典项列表的工具类
     *
     * @param code
     * @param siteId
     */
    public static List<DataDictVO> getItemList(String code, Long siteId) {
        List<DataDictItemEO> itemList = getDictItemList(code);
        if (null == itemList || itemList.isEmpty()) {
            return Collections.emptyList();
        }
        List<DataDictVO> list = new ArrayList<DataDictVO>();
        for (DataDictItemEO eo : itemList) {
            if (eo.getIsHide() == 1) {
                continue;
            }
            if (eo.getSiteId() == null || eo.getSiteId().equals(siteId)) {
                DataDictVO vo = new DataDictVO();
                vo.setId(eo.getItemId());
                vo.setPid(eo.getDataDicId());
                vo.setKey(eo.getName());
                vo.setValue(eo.getValue());
                vo.setCode(eo.getCode());
                vo.setDefault(eo.getIsDefault() == 1);

                list.add(vo);
            }
        }
        return list;
    }

    public static DataDictVO getDefuatItem(String pCode, Long siteId) {
        List<DataDictItemEO> itemList = getDictItemList(pCode);
        if (null == itemList || itemList.isEmpty()) {
            return null;
        }
        DataDictVO _vo = new DataDictVO();
        for (DataDictItemEO eo : itemList) {
            if (eo.getIsHide() == 1) {
                continue;
            }
            if (eo.getIsDefault() == 1 && (null == siteId || siteId.equals(eo.getSiteId()))) {
                _vo.setId(eo.getItemId());
                _vo.setCode(eo.getCode());
                _vo.setKey(eo.getName());
                _vo.setValue(eo.getValue());
                break;
            }
        }
        return _vo;
    }

    public static DataDictVO getItem(String pCode, String cCode) {
        List<DataDictItemEO> itemList = getDictItemList(pCode);
        if (null == itemList || itemList.isEmpty()) {
            return null;
        }
        DataDictVO _vo = new DataDictVO();
        for (DataDictItemEO eo : itemList) {
            if (eo.getIsHide() == 1) {
                continue;
            }
            if (eo.getCode().equals(cCode)) {
                _vo.setId(eo.getItemId());
                _vo.setCode(cCode);
                _vo.setKey(eo.getName());
                _vo.setValue(eo.getValue());
                break;
            }
        }
        return _vo;
    }
}