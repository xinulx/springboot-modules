package com.springboot.service.hibernate;

import com.springboot.entity.business.LimitMappingEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.QueryVO;
import com.springboot.service.common.IMockService;

public interface ILimitMappingService extends IMockService<LimitMappingEO> {
    Pagination getPage(QueryVO vo);

    void updateStatus(Long id, Integer isUsed);

    Long getCountByUrl(String url, Integer status);
}
