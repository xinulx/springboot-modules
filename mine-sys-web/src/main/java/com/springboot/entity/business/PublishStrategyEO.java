package com.springboot.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.entity.hibernate.impl.AMockEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @auth wangshibao
 * @Description: 发布策略实体类
 * @createDate 2019/4/24 8:55
 */
@Entity
@Table(name = "cms_publish_strategy")
public class PublishStrategyEO extends AMockEntity {

    //ID
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //对应站点ID
    @Column(name = "SITE_ID")
    private Long siteId;

    //是否启用 0:启用 1:关闭
    @Column(name = "IS_USED")
    private Integer isUsed = 0;

    //特殊双休日不拦截配置
    @Column(name = "SATURDAY")
    private String saturday;

    //特殊双休日不拦截配置
    @Column(name = "SUNDAY")
    private String sunday;

    //工作日开始时间
    @Column(name = "WORK_START_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date workStartDate;

    //工作日结束时间
    @Column(name = "WORK_END_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date workEndDate;

    //双休日开始时间
    @Column(name = "WEEK_START_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date weekStartDate;

    //双休日结束时间
    @Column(name = "WEEK_END_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date weekEndDate;

    //节假日时间 存储JOSNString
    @Column(name = "HOLIDAYS_DATES")
    private String holiDaysDates;

    //白名单用户id列表 存储用户id字符串
    @Column(name = "WHITELIST_USER_IDS")
    private String whiteListUserIds;

    //白名单用户名列表 存储用户名字符串
    @Column(name = "WHITELIST_USER_NAMES")
    private String whiteListUserNames;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public Date getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(Date workStartDate) {
        this.workStartDate = workStartDate;
    }

    public Date getWorkEndDate() {
        return workEndDate;
    }

    public void setWorkEndDate(Date workEndDate) {
        this.workEndDate = workEndDate;
    }

    public Date getWeekStartDate() {
        return weekStartDate;
    }

    public void setWeekStartDate(Date weekStartDate) {
        this.weekStartDate = weekStartDate;
    }

    public Date getWeekEndDate() {
        return weekEndDate;
    }

    public void setWeekEndDate(Date weekEndDate) {
        this.weekEndDate = weekEndDate;
    }

    public String getHoliDaysDates() {
        return holiDaysDates;
    }

    public void setHoliDaysDates(String holiDaysDates) {
        this.holiDaysDates = holiDaysDates;
    }

    public String getWhiteListUserIds() {
        return whiteListUserIds;
    }

    public void setWhiteListUserIds(String whiteListUserIds) {
        this.whiteListUserIds = whiteListUserIds;
    }

    public String getWhiteListUserNames() {
        return whiteListUserNames;
    }

    public void setWhiteListUserNames(String whiteListUserNames) {
        this.whiteListUserNames = whiteListUserNames;
    }
}
