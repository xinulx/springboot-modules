package com.springboot.dao.business;

import com.springboot.dao.hibernate.IMockDao;
import com.springboot.entity.business.BaseContentEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.ContentPageVO;

public interface IBaseContentDao extends IMockDao<BaseContentEO> {
    public Long getLastVersion();

    Pagination getPage(ContentPageVO pageVO);
}