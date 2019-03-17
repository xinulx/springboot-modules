package com.springboot.entity.business.report;

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
 * 信息公开统计上报申请信息
 * @author wangshibao
 * @date 2019-01-05
 */
@Entity
@Table(name = "CMS_PUBLIC_REPORT_APPLY")
public class PublicReportApplyEO extends AMockEntity {

    private static final long serialVersionUID = -3787526048029126039L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORGAN_ID")
    private Long organId; //上报单位

    @Column(name = "PUBLISH_USER_NAME")
    private String publishUserName; //发布人

    @Column(name = "YEAR")
    private String year; //填报年份

    @Column(name = "QUARTER")
    private String quarter; // 填报季度

    @Column(name = "HEAD_PERSON")
    private String headPerson; //单位负责人

    @Column(name = "REVIEWER")
    private String reviewer; //审核人

    @Column(name = "PREPARER")
    private String preparer; //填表人

    @Column(name = "CONCAT_PHONE")
    private String concatPhone; //联系电话

    @Column(name = "APPLY_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date applyDate; // 填表日期

    @Column(name = "SUM")
    private Integer sum; // 汇总结果

    private enum AuditStatus{
        TRUE,// 已审通过
        FALSE,// 未审核
        UN_REPORT// 未上报
    }

    @Column(name = "AUDIT_STATUS")
    private String auditStatus = AuditStatus.UN_REPORT.name(); // 上报状态

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganId() {
        return organId;
    }

    public void setOrganId(Long organId) {
        this.organId = organId;
    }

    public String getPublishUserName() {
        return publishUserName;
    }

    public void setPublishUserName(String publishUserName) {
        this.publishUserName = publishUserName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getHeadPerson() {
        return headPerson;
    }

    public void setHeadPerson(String headPerson) {
        this.headPerson = headPerson;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getPreparer() {
        return preparer;
    }

    public void setPreparer(String preparer) {
        this.preparer = preparer;
    }

    public String getConcatPhone() {
        return concatPhone;
    }

    public void setConcatPhone(String concatPhone) {
        this.concatPhone = concatPhone;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }
}
