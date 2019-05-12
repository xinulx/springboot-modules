package com.springboot.dao.business.impl;

import com.springboot.dao.business.IPublishStrategyDao;
import com.springboot.dao.hibernate.impl.MockDao;
import com.springboot.entity.business.PublishStrategyEO;
import org.springframework.stereotype.Repository;

@Repository
public class PublishStrategyDaoImpl extends MockDao<PublishStrategyEO> implements IPublishStrategyDao {

    @Override
    public PublishStrategyEO getEoBySiteId(Long siteId) {
        String hql = "from PublishStrategyEO where siteId=? and recordStatus= 'Normal'";
        return getEntityByHql(hql, new Object[]{siteId});
    }
}
