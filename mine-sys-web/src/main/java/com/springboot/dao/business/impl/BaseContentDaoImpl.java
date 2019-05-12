package com.springboot.dao.business.impl;

import com.springboot.common.util.AppUtil;
import com.springboot.dao.business.IBaseContentDao;
import com.springboot.dao.hibernate.impl.MockDao;
import com.springboot.entity.business.BaseContentEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.ContentPageVO;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository("baseContentDao")
public class BaseContentDaoImpl extends MockDao<BaseContentEO> implements IBaseContentDao {
    @Override
    public Long getLastVersion() {
        String hql = "select max(id) from BaseContentEO";
        Query query = getCurrentSession().createQuery(hql);
        Long maxid = (Long) query.uniqueResult();
        return maxid;
    }

    @Override
    public Pagination getPage(ContentPageVO pageVO) {
        StringBuffer hql = new StringBuffer("from BaseContentEO c where c.recordStatus='Normal'");

        Map<String, Object> map = new HashMap<String, Object>();
        if (!AppUtil.isEmpty(pageVO.getTitle())) {
            hql.append(" and c.title like :title");
            map.put("title", "%".concat(pageVO.getTitle()).concat("%"));
        }
        if (!AppUtil.isEmpty(pageVO.getSiteId())) {
            hql.append(" and c.siteId=:siteId");
            map.put("siteId", pageVO.getSiteId());
        }
        if (!AppUtil.isEmpty(pageVO.getColumnId())) {
            hql.append(" and c.columnId=:columnId");
            map.put("columnId", pageVO.getColumnId());
        }
        if (!AppUtil.isEmpty(pageVO.getCondition()) && pageVO.getStatus() != null) {
            hql.append(" and c." + pageVO.getCondition().trim() + "=:status");
            map.put("status", pageVO.getStatus());
        }
        if (!AppUtil.isEmpty(pageVO.getTypeCode())) {
            hql.append(" and c.typeCode=:typeCode");
            map.put("typeCode", pageVO.getTypeCode());
        }
        // 发送开始
        if (!AppUtil.isEmpty(pageVO.getStartTime())) {
            Date start = pageVO.getStartTime();
            hql.append(" and c.publishDate >= :startDate");
            map.put("startDate", start);
        }
        // 结束时间
        if (!AppUtil.isEmpty(pageVO.getEndTime())) {
            Date end = pageVO.getEndTime();
            Calendar date = Calendar.getInstance();
            date.setTime(end);
            date.set(Calendar.DATE, date.get(Calendar.DATE) + 1);// 结束日期增加一天
            end = date.getTime();
            hql.append(" and c.publishDate <= :endDate");
            map.put("endDate", end);
        }
        hql.append(" order by c.isTop desc,c.num desc,c.publishDate desc,c.id desc");
        return getPagination(pageVO.getPageIndex(), pageVO.getPageSize(), hql.toString(), map);
    }
}
