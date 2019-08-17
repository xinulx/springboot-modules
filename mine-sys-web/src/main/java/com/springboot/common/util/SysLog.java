package com.springboot.common.util;


import com.springboot.common.business.SpringContextHolder;
import com.springboot.service.hibernate.ICmsLogService;

/**
 * @ClassName: SysLog
 * @Description: 操作日志
 * @date 2015年12月31日 上午9:30:36
 */
public class SysLog {

    private static ICmsLogService cmsLogService = SpringContextHolder.getBean("cmsLogService");

    /**
     * @param desc     操作描述
     * @param caseType 业务对象类型,例如：UserEO
     * @param opr      操作类型 如CmsLogEO.Operation.Add.toString() (增加操作)
     * @return void   return type
     * @throws
     * @Title: log
     */
    public static void log(String desc, String caseType, String opr) {
        cmsLogService.recLog(desc, caseType, opr);
    }
}
