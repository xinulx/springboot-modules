package com.springboot.dao.business;

import com.springboot.dao.hibernate.IBaseDao;
import com.springboot.entity.business.ReleaseDataEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.vo.ReleaseDataQueryVO;

public interface IReleaseDataDao extends IBaseDao<ReleaseDataEO> {

    Pagination getPage(ReleaseDataQueryVO vo);
}
