package com.springboot.service.hibernate.impl;

import com.springboot.dao.business.ICmsLoginHistoryDao;
import com.springboot.entity.business.CmsLoginHistoryEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.service.common.impl.MockService;
import com.springboot.service.hibernate.ICmsLoginHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("cmsLoginHistoryService")
public class CmsLoginHistoryServiceImpl extends MockService<CmsLoginHistoryEO> implements ICmsLoginHistoryService {
	@Autowired
	private ICmsLoginHistoryDao cmsLoginHistoryDao;
	
	@Override
	public Pagination getPage(Long pageIndex, Integer pageSize, Date startDate,
							  Date endDate, String type, String key, Long siteId) {
		return (Pagination) cmsLoginHistoryDao.getPage(pageIndex, pageSize,
				startDate, endDate, type, key,siteId);
	}

	@Override
	public List<CmsLoginHistoryEO> getAllLogs(Date startDate, Date endDate,
			String type, String key,Long siteId) {
		return cmsLoginHistoryDao.getAllLogs(startDate, endDate, type, key,siteId);
	}
}
