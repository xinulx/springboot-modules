package com.springboot.dao.business;

import com.springboot.dao.hibernate.IBaseDao;
import com.springboot.entity.business.GrabConfigEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.ContentPageVO;

public interface IGrabConfigDao extends IBaseDao<GrabConfigEO> {
    Pagination getPage(ContentPageVO pageVO);
}