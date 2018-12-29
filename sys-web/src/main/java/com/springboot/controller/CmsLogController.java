package com.springboot.controller;

import com.springboot.entity.vo.ResponseData;
import com.springboot.common.util.CSVUtils;
import com.springboot.entity.business.CmsLogEO;
import com.springboot.entity.business.CmsLoginHistoryEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.service.hibernate.ICmsLogService;
import com.springboot.service.hibernate.ICmsLoginHistoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: CmsLogController
 * @Description: 系统日志控制器
 * @date 2015年8月25日 上午10:43:29
 */
@Controller
@RequestMapping("sysLog")
public class CmsLogController {
    @Autowired
    private ICmsLogService cmsLogService;
    @Autowired
    private ICmsLoginHistoryService cmsLoginHistoryService;


    /**
     * @return
     * @Description 日志主页
     */
    @RequestMapping("index")
    public String index() {
        return "/log/log_index";
    }

    /**
     * @return
     * @Description 保存日志
     */
    @RequestMapping("saveLog")
    @ResponseBody
    public Object saveLog(String description, String caseType, String operation) {
        return ResponseData.success();
    }

    /**
     * 登录日志
     *
     * @return
     */
    @RequestMapping("loginLogPage")
    public String loginLogPage() {
        return "/log/loginLog_list";
    }

    /**
     * 管理日志
     *
     * @return
     */
    @RequestMapping("mgrLogPage")
    public String mgrLogPage() {
        return "/log/opeLog_list";
    }

    /**
     * 管理日志查询
     *
     * @param pageIndex
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param type
     * @param key
     * @return
     * @author yy
     */
    @RequestMapping("getPage")
    @ResponseBody
    public Object getPage(Long pageIndex, Integer pageSize, String startDate, String endDate, String type, String key) {
        Long siteId = null;
        if (pageIndex == null || pageIndex < 0) {
            pageIndex = 0L;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 15;
        }
        Date start = null;
        Date end = null;
        if (null != StringUtils.trimToNull(startDate)) {
            start = formatStringToTime(startDate, "yyyy-MM-dd HH:mm:ss");
        }
        if (null != StringUtils.trimToNull(endDate)) {
            endDate = endDate.replace("00:00:00", "23:59:59");
            end = formatStringToTime(endDate, "yyyy-MM-dd HH:mm:ss");
        }
        Pagination page = null;
        page = cmsLogService.getPage(pageIndex, pageSize, start, end, type, key, siteId);
        return page;
    }

    /**
     * 登录日志查询
     *
     * @param pageIndex
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param type
     * @param key
     * @return
     * @author yy
     */
    @RequestMapping("getLoginPage")
    @ResponseBody
    public Object getLoginPage(Long pageIndex, Integer pageSize, String startDate, String endDate, String type, String key) {
        Long siteId = null;
        if (pageIndex == null || pageIndex < 0) {
            pageIndex = 0L;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 15;
        }
        Date start = null;
        Date end = null;
        if (null != StringUtils.trimToNull(startDate)) {
            start = formatStringToTime(startDate, "yyyy-MM-dd HH:mm:ss");
        }
        if (null != StringUtils.trimToNull(endDate)) {
            endDate = endDate.replace("00:00:00", "23:59:59");
            end = formatStringToTime(endDate, "yyyy-MM-dd HH:mm:ss");
        }
        Pagination page = null;
        page = cmsLoginHistoryService.getPage(pageIndex, pageSize, start, end, type, key, siteId);
        return page;
    }

    /***
     * 导出管理日志
     * @param response
     */
    @RequestMapping("getLogExport")
    public void getLogExport(HttpServletResponse response, String startDate, String endDate, String type, String key) {
        Date start = null;
        Date end = null;
        Long siteId = null;
        if (null != StringUtils.trimToNull(startDate)) {
            start = formatStringToTime(startDate, "yyyy-MM-dd HH:mm:ss");
        }
        if (null != StringUtils.trimToNull(endDate)) {
            endDate = endDate.replace("00:00:00", "23:59:59");
            end = formatStringToTime(endDate, "yyyy-MM-dd HH:mm:ss");
        }
        List<CmsLogEO> logs = null;
        logs = cmsLogService.getAllLogs(start, end, type, key, null);
        //文件头
        String[] titles = new String[]{"用户名", "姓名", "单位名称", "IP", "操作时间", "维护内容"};
        //内容
        List<String[]> datas = new ArrayList<String[]>();
        if (logs != null && logs.size() > 0) {
            for (CmsLogEO log : logs) {
                String[] row1 = new String[6];
                row1[0] = log.getUid();
                row1[1] = log.getCreateUser();
                row1[2] = log.getOrganName();
                row1[3] = log.getOperationIp();
                row1[4] = formatTimeToString(log.getCreateDate(), "yyyy-MM-dd HH:mm:ss");
                row1[5] = log.getDescription();

                datas.add(row1);
            }
        }

        //导出
        String name = System.currentTimeMillis()+"";
        try {
            CSVUtils.download(name, titles, datas, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * 导出登录日志
     * @param response
     */
    @RequestMapping("getLogHistoryExport")
    public void getLogHistoryExport(HttpServletResponse response, String startDate, String endDate, String type, String key) {
        Date start = null;
        Date end = null;
        Long siteId = null;
        if (null != StringUtils.trimToNull(startDate)) {
            start = formatStringToTime(startDate, "yyyy-MM-dd HH:mm:ss");
        }
        if (null != StringUtils.trimToNull(endDate)) {
            endDate = endDate.replace("00:00:00", "23:59:59");
            end = formatStringToTime(endDate, "yyyy-MM-dd HH:mm:ss");
        }
        List<CmsLoginHistoryEO> logs = null;
        logs = cmsLoginHistoryService.getAllLogs(start, end, type, key, null);
        //文件头
        String[] titles = new String[]{"用户名", "姓名", "单位名称", "登录IP", "登录时间", "描述"};
        //内容
        List<String[]> datas = new ArrayList<String[]>();
        if (logs != null && logs.size() > 0) {
            for (CmsLoginHistoryEO log : logs) {
                String[] row1 = new String[6];
                row1[0] = log.getUid();
                row1[1] = log.getCreateUser();
                row1[2] = log.getOrganName();
                row1[3] = log.getLoginIp();
                row1[4] = formatTimeToString(log.getCreateDate(), "yyyy-MM-dd HH:mm:ss");
                row1[5] = log.getDescription();
                datas.add(row1);
            }
        }

        //导出
        String name = System.currentTimeMillis()+"";
        try {
            CSVUtils.download(name, titles, datas, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Date formatStringToTime(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date result = null;
        try {
            result = format.parse(str);
        } catch (ParseException e) {
            return null;
        }
        return result;
    }

    public String formatTimeToString(Date date, String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        if (date != null) {
            return f.format(date);
        }
        return null;
    }
}
