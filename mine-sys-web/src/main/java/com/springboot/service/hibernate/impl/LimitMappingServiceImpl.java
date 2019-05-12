package com.springboot.service.hibernate.impl;

import com.springboot.dao.business.ILimitMappingDao;
import com.springboot.entity.business.LimitMappingEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.QueryVO;
import com.springboot.service.common.impl.MockService;
import com.springboot.service.hibernate.ILimitMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("limitMappingService")
public class LimitMappingServiceImpl extends MockService<LimitMappingEO> implements ILimitMappingService {

    @Autowired
    private ILimitMappingDao limitMappingDao;

    @Override
    public Pagination getPage(QueryVO vo) {
        return limitMappingDao.getPage(vo);
    }

    @Override
    public void updateStatus(Long id, Integer isUsed) {
        limitMappingDao.updateStatus(id,isUsed);
    }

    @Override
    public Long getCountByUrl(String url, Integer status) {
        return limitMappingDao.getCountByUrl(url,status);
    }
}
