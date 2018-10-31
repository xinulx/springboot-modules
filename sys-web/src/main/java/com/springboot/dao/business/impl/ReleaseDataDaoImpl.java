package com.springboot.dao.business.impl;

import com.springboot.dao.business.IReleaseDataDao;
import com.springboot.dao.hibernate.impl.BaseDao;
import com.springboot.entity.business.ReleaseDataEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.vo.ReleaseDataQueryVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("ReleaseDataDao")
public class ReleaseDataDaoImpl extends BaseDao<ReleaseDataEO> implements IReleaseDataDao {
    @Override
    public Pagination getPage(ReleaseDataQueryVO vo) {
        List values = new ArrayList();
        StringBuffer hql = new StringBuffer(" from ReleaseDataEO where 1=1 ");
        if(StringUtils.isNotEmpty(vo.getName())){
            hql.append(" and itemName like ?");
            values.add("%".concat(vo.getName()).concat("%"));
        }
        if(vo.getPeriodDate() != null){
            hql.append(" and periodDate = ?");
            values.add(vo.getPeriodDate());
        }
        if(StringUtils.isNotEmpty(vo.getSortField())){
            hql.append(" order by "+vo.getSortField() + " " + vo.getSortOrder());
        }else{
            hql.append(" order by createDate desc");
        }
        return getPagination(vo.getPageIndex(),vo.getPageSize(),hql.toString(),values.toArray());
    }
}
