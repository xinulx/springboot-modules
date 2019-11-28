package com.springboot.service.hibernate;

import com.springboot.dao.business.IGrabConfigDao;
import com.springboot.entity.business.GrabConfigEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.ContentPageVO;
import com.springboot.service.common.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("grabConfigService")
public class GrabConfigServiceImpl extends BaseService<GrabConfigEO> implements IGrabConfigService {

    @Autowired
    private IGrabConfigDao grabConfigDao;
    @Override
    public Pagination getPage(ContentPageVO pageVO) {
        return grabConfigDao.getPage(pageVO);
    }
}
