package com.springboot.service.hibernate.impl;

import com.springboot.dao.business.IBaseContentDao;
import com.springboot.entity.business.BaseContentEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.ContentPageVO;
import com.springboot.service.common.impl.MockService;
import com.springboot.service.hibernate.IBaseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ContentServiceImpl
 * @Description: 新闻信息业务逻辑
 */
@Service("baseContentService")
public class BaseContentServiceImpl extends MockService<BaseContentEO> implements IBaseContentService {
    @Autowired
    private IBaseContentDao baseContentDao;

    @Override
    public Pagination getPage(ContentPageVO pageVO) {
        return baseContentDao.getPage(pageVO);
    }

}