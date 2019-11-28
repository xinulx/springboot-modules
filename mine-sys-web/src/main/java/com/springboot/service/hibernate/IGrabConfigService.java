package com.springboot.service.hibernate;

import com.springboot.entity.business.GrabConfigEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.ContentPageVO;
import com.springboot.service.common.IBaseService;

public interface IGrabConfigService extends IBaseService<GrabConfigEO> {
    Pagination getPage(ContentPageVO pageVO);
}
