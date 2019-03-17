package com.springboot.dao.business.impl;

import com.springboot.dao.business.ICmsLogDao;
import com.springboot.dao.hibernate.impl.MockDao;
import com.springboot.entity.business.CmsLogEO;
import com.springboot.entity.hibernate.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository("cmsLogDao")
public class CmsLogDaoImpl extends MockDao<CmsLogEO> implements ICmsLogDao {

    @Override
    public Pagination getPage(Long pageIndex, Integer pageSize, Date startDate, Date endDate, String type, String key, Long siteId) {
        String hql = " from CmsLogEO t where  t.recordStatus='Normal' ";
        List<Object> values = new ArrayList<Object>();
        
        if(null!=startDate) {
            hql += " and t.createDate>? ";
            values.add(startDate);
        }
        if(null!=endDate) {
            hql += " and t.createDate<? ";
            values.add(endDate);
        }
        if(null!= StringUtils.trimToNull(type) && null!= StringUtils.trimToNull(key)) {
        	if("deion".equals(type)){
        		hql += "and t.description like '%"+key+"%'";
        	}else{
        		hql += "and t."+type+" like '%"+key+"%'";
        	}
        }
        if(null!=siteId){
        	hql+=" and siteId=?";
        	 values.add(siteId);
        }
        hql += " order by t.createDate desc ";
        return (Pagination) getPagination(pageIndex, pageSize, hql, values.toArray());
    }

	@Override
	public List<CmsLogEO> getAllLogs(Date startDate, Date endDate, String type,
			String key,Long siteId) {
		String hql = " from CmsLogEO t where  t.recordStatus='Normal' ";
        List<Object> values = new ArrayList<Object>();
        
        if(null!=startDate) {
            hql += " and t.createDate>? ";
            values.add(startDate);
        }
        if(null!=endDate) {
            hql += " and t.createDate<? ";
            values.add(endDate);
        }
        if(null!= StringUtils.trimToNull(type) && null!= StringUtils.trimToNull(key)) {
            hql += "and t."+type+" like '%"+key+"%'"; 
        }
        if(null!=siteId){
        	hql+=" and siteId=?";
        	 values.add(siteId);
        }
        hql += " order by t.createDate desc ";
        return getEntitiesByHql(hql, values.toArray());
	}
    
}
