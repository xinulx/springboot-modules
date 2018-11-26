package com.springboot.service.hibernate;


import com.springboot.entity.business.CmsLoginHistoryEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.service.common.IMockService;

import java.util.Date;
import java.util.List;


/**
 * @ClassName: ICmsLoginHistoryService
 * @Description: 登录历史日志服务类
 * @date 2015年8月25日 上午10:54:08
 */
public interface ICmsLoginHistoryService extends IMockService<CmsLoginHistoryEO> {


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
     *
     * @param startDate
     * @param endDate
     * @param type
     * @param key
     * @return
     */
    public List<CmsLoginHistoryEO> getAllLogs(Date startDate, Date endDate, String type, String key, Long siteId);
}

