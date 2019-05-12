package com.springboot.service.hibernate;

import com.springboot.entity.business.PublishStrategyEO;
import com.springboot.service.common.IMockService;

public interface IPublishStrategyService extends IMockService<PublishStrategyEO> {

    PublishStrategyEO saveOrUpdateConfigEO(PublishStrategyEO eo);

    PublishStrategyEO getEOBySiteId(Long siteId);
}
