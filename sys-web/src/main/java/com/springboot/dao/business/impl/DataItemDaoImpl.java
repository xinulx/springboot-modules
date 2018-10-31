package com.springboot.dao.business.impl;

import com.springboot.dao.business.IDataItemDao;
import com.springboot.dao.hibernate.impl.BaseDao;
import com.springboot.entity.business.DataItemEO;
import com.springboot.vo.DataItemQueryVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("dataItemDao")
public class DataItemDaoImpl extends BaseDao<DataItemEO> implements IDataItemDao {
    @Override
    public Object getPage(DataItemQueryVO vo) {
        Map values = new HashMap();
        if(StringUtils.isNotEmpty(vo.getName())){
            values.put("itemName",vo.getName());
            List<DataItemEO> entities = getEntities(DataItemEO.class, values);
            // 按名称查询时，带出分类或所含数据项
            if(entities != null && entities.size() == 1){
                Long parentId = entities.get(0).getParentId();
                Long id = entities.get(0).getId();
                Map childMap = new HashMap();
                // 如果为分类
                if(parentId == 0){
                    childMap.put("parentId",id);
                // 如果为数据项
                }else{
                    childMap.put("id",parentId);
                }
                List childs = getEntities(DataItemEO.class, childMap);
                entities.addAll(childs);
                return entities;
            }
        }
        return getEntities(DataItemEO.class,values);
    }
}
