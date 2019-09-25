package com.springboot.tag.generate;

import com.springboot.dao.business.IDataItemDao;
import com.springboot.entity.business.DataItemEO;
import com.springboot.tag.common.AbstractBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取数据项列表<br/>
 *
 * @author wangshibao <br/>
 * @version v1.0 <br/>
 * @date 2018-11-06<br/>
 */
@Component
public class DataItemListBeanService extends AbstractBeanService {

    @Autowired
    private IDataItemDao dataItemDao;

    @Override
    public String objToStr() {
        // 分类数据项parentId为0
        Long parentId = 0L;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("parentId", parentId);
        List<Map<String, Object>> resultData = new ArrayList<>();
        List<DataItemEO> parentEOs = dataItemDao.getEntities(DataItemEO.class, paramMap);
        if (parentEOs == null) {
            parentEOs = new ArrayList<>();
        }
        for (DataItemEO dataItemEO : parentEOs) {
            paramMap.put("parentId", dataItemEO.getId());
            List<DataItemEO> childEOs = dataItemDao.getEntities(DataItemEO.class, paramMap);
            if (childEOs == null) {
                childEOs = new ArrayList<>();
            }
            List<Map<String, Object>> childs = new ArrayList<>();
            for (DataItemEO itemEO : childEOs) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("name", itemEO.getItemName());
                itemMap.put("importName", itemEO.getItemImportName());
                itemMap.put("unit", itemEO.getItemUnit());
                itemMap.put("desc", itemEO.getItemDesc());//指标解释
                childs.add(itemMap);
            }
            Map<String, Object> childItemMap = new HashMap<>();
            childItemMap.put("name", dataItemEO.getItemName());
            childItemMap.put("importName", dataItemEO.getItemImportName());
            childItemMap.put("unit", dataItemEO.getItemUnit());
            childItemMap.put("desc", dataItemEO.getItemDesc());
            childItemMap.put("children", childs);
            resultData.add(childItemMap);
        }
        return processHtml(resultData);
    }
}
