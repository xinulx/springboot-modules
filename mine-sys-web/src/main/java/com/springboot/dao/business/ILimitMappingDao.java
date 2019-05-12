package com.springboot.dao.business;

import com.springboot.dao.hibernate.IMockDao;
import com.springboot.entity.business.LimitMappingEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.QueryVO;

public interface ILimitMappingDao extends IMockDao<LimitMappingEO> {

    Pagination getPage(QueryVO vo);

    void updateStatus(Long id, Integer isUsed);

    Long getCountByUrl(String url, Integer status);
}
