package com.springboot.dao.business;


import com.springboot.dao.hibernate.IMockDao;
import com.springboot.entity.business.CmsLogEO;
import com.springboot.entity.hibernate.Pagination;

import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName: ICmsLogDao
 * @Description: 操作日志数据访问层
 * @date 2015年8月25日 上午10:54:50
 *
 */
public interface ICmsLogDao extends IMockDao<CmsLogEO> {
    /**
     * 日志查询
     * 
     * @param pageIndex
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param type
     * @param key
     * @return
     */
    public Pagination getPage(Long pageIndex, Integer pageSize, Date startDate, Date endDate, String type, String key, Long siteId);

    /**
     * 获取所有日志
     * @param startDate
     * @param endDate
     * @param type
     * @param key
     * @return
     */
    public List<CmsLogEO> getAllLogs(Date startDate, Date endDate, String type, String key, Long siteId);
}
