package com.springboot.tag.generate;

import com.springboot.dao.business.IDataItemDao;
import com.springboot.dao.business.IReleaseDataDao;
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
    @Autowired
    private IReleaseDataDao releaseDataDao;


    @Override
    public String objToStr() {
        // 0-最新 1-进度 2-年度
        String type = getParamObj().getString("type");
        // 分类数据项parentId为0
        Long parentId = 0L;
        Map<String, Object> paramMap = new HashMap<>();
        //paramMap.put("itemType", Integer.parseInt(type) == 0 ? 1 : Integer.parseInt(type));
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
            boolean hasData = false;//是否有子项存在数据
            for (DataItemEO itemEO : childEOs) {
                // 如果为最新数据，则过滤出[6月前-至现在]有数据的数据项
                if ("0".equals(type)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("itemImportName", itemEO.getItemImportName());
                    Long hasDataCount = releaseDataDao.getHasDataCount(map);
                    if (hasDataCount > 0) {
                        hasData = true;
                    } else {
                        continue;
                    }
                } else {
                    hasData = true;
                }
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("name", itemEO.getItemName());
                // 导入名称,用于匹配导入数据
                itemMap.put("importName", itemEO.getItemImportName());
                // 子数据项需要带出单位
                itemMap.put("unit", itemEO.getItemUnit());
                itemMap.put("desc", itemEO.getItemDesc());//指标解释
                childs.add(itemMap);
            }
            if (hasData) {
                Map<String, Object> childItemMap = new HashMap<>();
                childItemMap.put("name", dataItemEO.getItemName());
                childItemMap.put("desc", dataItemEO.getItemDesc());//默认展示分类项解读
                childItemMap.put("children", childs);
                resultData.add(childItemMap);
            }
        }
        return processHtml(resultData);
    }
}
