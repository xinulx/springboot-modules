package com.springboot.dao.business.impl;

import com.springboot.common.util.AppUtil;
import com.springboot.dao.business.IGrabConfigDao;
import com.springboot.dao.hibernate.impl.BaseDao;
import com.springboot.entity.business.GrabConfigEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.ContentPageVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("grabConfigDao")
public class GrabConfigDaoImpl extends BaseDao<GrabConfigEO> implements IGrabConfigDao {
    @Override
    public Pagination getPage(ContentPageVO pageVO) {
        StringBuffer hql = new StringBuffer("from GrabConfigEO c where 1 = 1");

        Map<String, Object> map = new HashMap<String, Object>();
        if (!AppUtil.isEmpty(pageVO.getTitle())) {
            hql.append(" and c.name like :title");
            map.put("title", "%".concat(pageVO.getTitle()).concat("%"));
        }
        hql.append(" order by c.createDate desc,c.id desc");
        return getPagination(pageVO.getPageIndex(), pageVO.getPageSize(), hql.toString(), map);
    }
}
