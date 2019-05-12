package com.springboot.dao.business.impl;

import com.springboot.common.util.AppUtil;
import com.springboot.dao.business.ILimitMappingDao;
import com.springboot.dao.hibernate.impl.MockDao;
import com.springboot.entity.business.LimitMappingEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.QueryVO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class LimitMappingDaoImpl extends MockDao<LimitMappingEO> implements ILimitMappingDao {
    @Override
    public Pagination getPage(QueryVO vo) {
        StringBuilder hql = new StringBuilder("from LimitMappingEO where 1=1");
        List values = new ArrayList();
        if (!AppUtil.isEmpty(vo.getSearchText())) {
            hql.append(" and (className like ? or methodName like ?)");
            values.add("%".concat(vo.getSearchText()).concat("%"));
            values.add("%".concat(vo.getSearchText()).concat("%"));
        }
        if (!AppUtil.isEmpty(vo.getType())) {
            hql.append(" and isUsed = ?");
            values.add(vo.getType());
        }
        return getPagination(vo.getPageIndex(), vo.getPageSize(), hql.toString(), values.toArray());
    }

    @Override
    public void updateStatus(Long id, Integer isUsed) {
        StringBuilder hql = new StringBuilder("update LimitMappingEO set isUsed = ?,updateDate=?,usedDate=? where id = ?");
        List values = new ArrayList();
        values.add(isUsed);
        values.add(new Date());
        values.add(isUsed == 1 ? new Date() : null);
        values.add(id);
        executeUpdateByHql(hql.toString(), values.toArray());
    }

    @Override
    public Long getCountByUrl(String url, Integer status) {
        StringBuilder hql = new StringBuilder("from LimitMappingEO where url=?");
        List values = new ArrayList();
        values.add(url);
        if (!AppUtil.isEmpty(status)) {
            hql.append(" and isUsed = ?");
            values.add(status);
        }
        return getCount(hql.toString(), values.toArray());
    }
}
