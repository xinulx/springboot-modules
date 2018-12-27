package com.springboot.entity.business.dataopen;

import com.springboot.entity.hibernate.impl.AMockEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 资源目录<br/>
 *
 * @author wangshibao <br/>
 * @version v1.0 <br/>
 * @date 2017-10-19<br/>
 */
@Entity
@Table(name = "data_open_resource_catalog")
public class ResourceCatalogEO extends AMockEntity {
    private static final long serialVersionUID = -1300742296285581640L;


    public static Map<Integer, String> UpdateCycleMap;

    static {
        UpdateCycleMap = new HashMap<Integer, String>();
        //经过协商，这边实时数据也是每天更新，所以此处不显示实时
        UpdateCycleMap.put(0, "每日");
        UpdateCycleMap.put(1, "每日");
        UpdateCycleMap.put(2, "每周");
        UpdateCycleMap.put(3, "每月");
        UpdateCycleMap.put(4, "每两个月");
        UpdateCycleMap.put(5, "每季度");
        UpdateCycleMap.put(6, "每半年");
        UpdateCycleMap.put(7, "每年");
        UpdateCycleMap.put(8, "每三年");
        UpdateCycleMap.put(9, "不定期");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    /**
     * 资源名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 数据表名字
     */
    @Column(name = "DATA_TABLE_NAME")
    private String dataTableName;


    /**
     * 版本标识（唯一）
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 版本号
     */
    @Column(name = "VERSION")
    private String version;


    /**
     * 数据领域
     */
    @Column(name = "DATA_AREA")
    private Long dataArea;

    /**
     * 提供机构
     */
    @Column(name = "PROVIDER_ORGAN")
    private Long providerOrgan;

    /**
     * 更新周期
     */
    @Column(name = "UPDATE_CYCLE")
    private Integer updateCycle = 9;//默认不定期

    /**
     * 资源摘要
     */
    @Column(name = "RESOURCE_ABSTRACT")
    private String resourceAbstract;

    /**
     * 下载次数
     */
    @Column(name = "DOWNLOAD_TIMES")
    private Long downloadTimes = 0L;

    /**
     * 浏览次数
     */
    @Column(name = "view_times")
    private Long viewTimes = 0L;

    /**
     * 数据量
     */
    @Column(name = "DATA_COUNTS")
    private Long dataCounts = 0L;

    /**
     * 评分
     */
    @Column(name = "GRADE")
    private Float grade = 0.0f;

    /**
     * 评分次数
     */
    @Column(name = "GRADE_TIMES")
    private Long gradeTimes = 0L;


    /**
     * 分享次数
     */
    @Column(name = "SHARE_TIMES")
    private Long shareTimes = 0L;


    /**
     * 数据更新时间
     */
    @Column(name = "REFRESH_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date refreshDate;

    /**
     * 发布时间
     */
    @Column(name = "PUBLISH_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date publishDate;

    /**
     * 数据领域编码-同步数据用
     */
    @Column(name = "RESOURCESUBJECT")
    private String resourceSubject;

    /**
     * 前段码-同步数据用
     */
    @Column(name = "OUCODE")
    private String oucode;


    /**
     * 提供机构名
     */
    @Transient
    private String providerOrganName;

    /**
     * 数据领域名
     */
    @Transient
    private String dataAreaName;

    /**
     * 更新周期描述
     */
    @Transient
    private String updateCycleDesc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataTableName() {
        return dataTableName;
    }

    public void setDataTableName(String dataTableName) {
        this.dataTableName = dataTableName;
    }

    public Long getDataArea() {
        return dataArea;
    }

    public void setDataArea(Long dataArea) {
        this.dataArea = dataArea;
    }

    public Long getProviderOrgan() {
        return providerOrgan;
    }

    public void setProviderOrgan(Long providerOrgan) {
        this.providerOrgan = providerOrgan;
    }

    public Integer getUpdateCycle() {
        return updateCycle;
    }

    public void setUpdateCycle(Integer updateCycle) {
        this.updateCycle = updateCycle;
    }

    public String getResourceAbstract() {
        return resourceAbstract;
    }

    public void setResourceAbstract(String resourceAbstract) {
        this.resourceAbstract = resourceAbstract;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getProviderOrganName() {
        return providerOrganName;
    }

    public void setProviderOrganName(String providerOrganName) {
        this.providerOrganName = providerOrganName;
    }

    public String getDataAreaName() {
        return dataAreaName;
    }

    public void setDataAreaName(String dataAreaName) {
        this.dataAreaName = dataAreaName;
    }

    public String getUpdateCycleDesc() {
        return updateCycleDesc;
    }

    public void setUpdateCycleDesc(String updateCycleDesc) {
        this.updateCycleDesc = updateCycleDesc;
    }

    public String getResourceSubject() {
        return resourceSubject;
    }

    public void setResourceSubject(String resourceSubject) {
        this.resourceSubject = resourceSubject;
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode;
    }

    public Long getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(Long downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

    public Date getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(Date refreshDate) {
        this.refreshDate = refreshDate;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public Long getDataCounts() {
        return dataCounts;
    }

    public void setDataCounts(Long dataCounts) {
        this.dataCounts = dataCounts;
    }

    public Long getGradeTimes() {
        return gradeTimes;
    }

    public void setGradeTimes(Long gradeTimes) {
        this.gradeTimes = gradeTimes;
    }

    public Long getViewTimes() {
        return viewTimes;
    }

    public void setViewTimes(Long viewTimes) {
        this.viewTimes = viewTimes;
    }

    public Long getShareTimes() {
        return shareTimes;
    }

    public void setShareTimes(Long shareTimes) {
        this.shareTimes = shareTimes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
