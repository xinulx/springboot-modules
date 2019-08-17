package com.springboot.service.hibernate.impl;

import com.springboot.common.util.BrowserUtils;
import com.springboot.common.util.IpUtil;
import com.springboot.common.util.LoginPersonUtil;
import com.springboot.common.util.ThreadUtil;
import com.springboot.dao.business.ICmsLogDao;
import com.springboot.entity.business.CmsLogEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.service.common.impl.MockService;
import com.springboot.service.hibernate.ICmsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service("cmsLogService")
public class CmsLogServiceImpl extends MockService<CmsLogEO> implements ICmsLogService {
    @Autowired
    private ICmsLogDao cmsLogDao;

    @Override
    public void recLog(String description, String caseType, String operation) {
        HttpServletRequest request;
        try {
            request = LoginPersonUtil.getRequest();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        CmsLogEO logEO = new CmsLogEO();
        logEO.setCaseType(caseType);
        logEO.setOperation(operation);
        logEO.setDescription(description);
        logEO.setOperationIp(IpUtil.getIpAddr(request));
        logEO.setCreateUser(ThreadUtil.getString(ThreadUtil.LocalParamsKey.PersonName));
        logEO.setOrganName(ThreadUtil.getString(ThreadUtil.LocalParamsKey.OrganName));
        logEO.setUid(ThreadUtil.getString(ThreadUtil.LocalParamsKey.UserName));
        logEO.setUserAgent(request.getHeader("user-agent"));
        logEO.setRequestUri(request.getRequestURI());
        logEO.setMethod(request.getMethod());
        logEO.setBroswer(BrowserUtils.getBrowserType(request));
        logEO.setSiteId(LoginPersonUtil.getSiteId());
        cmsLogDao.save(logEO);
    }

    @Override
    public void deleteLog(Long logId) {
        cmsLogDao.delete(CmsLogEO.class, logId);
    }

    @Override
    public Pagination getPage(Long pageIndex, Integer pageSize, Date startDate, Date endDate, String type, String key, Long siteId) {
        return cmsLogDao.getPage(pageIndex, pageSize, startDate, endDate, type, key, siteId);
    }

    @Override
    public List<CmsLogEO> getAllLogs(Date startDate, Date endDate, String type,
                                     String key, Long siteId) {
        return cmsLogDao.getAllLogs(startDate, endDate, type, key, siteId);
    }
}
