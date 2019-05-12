package com.springboot.service.hibernate;

import com.springboot.entity.business.BaseContentEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.ContentPageVO;
import com.springboot.service.common.IMockService;

public interface IBaseContentService extends IMockService<BaseContentEO> {

    public Pagination getPage(ContentPageVO queryVO);
}
