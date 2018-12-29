package com.springboot.dao.business;

import com.springboot.dao.hibernate.IMockDao;
import com.springboot.entity.business.HbUserEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.QueryRoleVO;

public interface IUserDao extends IMockDao<HbUserEO> {

    /**
     * 分页查询
     */
    Pagination getPage(QueryRoleVO queryRoleVO);
}
