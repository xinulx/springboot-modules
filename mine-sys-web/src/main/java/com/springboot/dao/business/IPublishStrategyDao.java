package com.springboot.dao.business;


import com.springboot.dao.hibernate.IMockDao;
import com.springboot.entity.business.PublishStrategyEO;

public interface IPublishStrategyDao extends IMockDao<PublishStrategyEO> {

    public PublishStrategyEO getEoBySiteId(Long siteId);

}
