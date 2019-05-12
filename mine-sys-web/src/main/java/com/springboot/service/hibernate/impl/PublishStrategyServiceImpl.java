package com.springboot.service.hibernate.impl;

import com.springboot.dao.business.IPublishStrategyDao;
import com.springboot.entity.business.PublishStrategyEO;
import com.springboot.service.common.impl.MockService;
import com.springboot.service.hibernate.IPublishStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("publishStrategyService")
public class PublishStrategyServiceImpl extends MockService<PublishStrategyEO> implements IPublishStrategyService {

    @Autowired
    private IPublishStrategyDao publishStrategyDao;

    @Override
    public PublishStrategyEO saveOrUpdateConfigEO(PublishStrategyEO eo) {
        if (eo.getId() != null) {
            publishStrategyDao.merge(eo);
        }else{
            publishStrategyDao.save(eo);
        }
        return eo;
    }

    @Override
    public PublishStrategyEO getEOBySiteId(Long siteId) {
        if (publishStrategyDao.getEoBySiteId(siteId) == null) {
            return new PublishStrategyEO();
        } else {
            return publishStrategyDao.getEoBySiteId(siteId);
        }
    }
}