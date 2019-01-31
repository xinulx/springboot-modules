package com.springboot.entity.business.report;


import com.springboot.entity.hibernate.impl.AMockEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 各单位信息公开报表统计上报结果
 * @author wangshibao
 * @date 2019-01-05
 */

@Entity
@Table(name = "CMS_PUBLIC_REPORT_RESULT")
public class PublicReportResultEO extends AMockEntity {

    private static final long serialVersionUID = 8307302623013952844L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "REPORT_APPLY_ID")
    private Long reportApplyId; // 上报申请ID

    @Column(name = "REPORT_ITEM_ID")
    private Long reportItemId; // 上报项ID，对应【统计报表配置】中的树节点ID

    @Column(name = "ORGAN_ID")
    private Long organId; //上报单位

    @Column(name = "REPORT_TYPE")
    private String reportType; // 上报的数据类型

    @Column(name = "REPORT_YEAR")
    private String reportYear; // 上报年份

    @Column(name = "QUARTER_TYPE")
    private String quarterType; // 上报季度，全年、一季度、二季度、三季度、四季度

    @Column(name = "CITY_COUNT")
    private Integer cityCount; // 市政府数量

    @Column(name = "CITY_UNIT_COUNT")
    private Integer cityUnitCount; // 市直属单位数量

    @Column(name = "COUNTY_COUNT")
    private Integer countyCount; // 县区数量

    @Column(name = "COUNTY_UNIT_COUNT")
    private Integer countyUnitCount; // 县区直属数量

    @Column(name = "TOWN_COUNT")
    private Integer townCount; // 乡镇数量

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportApplyId() {
        return reportApplyId;
    }

    public void setReportApplyId(Long reportApplyId) {
        this.reportApplyId = reportApplyId;
    }

    public Long getReportItemId() {
        return reportItemId;
    }

    public void setReportItemId(Long reportItemId) {
        this.reportItemId = reportItemId;
    }

    public Long getOrganId() {
        return organId;
    }

    public void setOrganId(Long organId) {
        this.organId = organId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportYear() {
        return reportYear;
    }

    public void setReportYear(String reportYear) {
        this.reportYear = reportYear;
    }

    public String getQuarterType() {
        return quarterType;
    }

    public void setQuarterType(String quarterType) {
        this.quarterType = quarterType;
    }

    public Integer getCityCount() {
        return cityCount;
    }

    public void setCityCount(Integer cityCount) {
        this.cityCount = cityCount;
    }

    public Integer getCityUnitCount() {
        return cityUnitCount;
    }

    public void setCityUnitCount(Integer cityUnitCount) {
        this.cityUnitCount = cityUnitCount;
    }

    public Integer getCountyCount() {
        return countyCount;
    }

    public void setCountyCount(Integer countyCount) {
        this.countyCount = countyCount;
    }

    public Integer getCountyUnitCount() {
        return countyUnitCount;
    }

    public void setCountyUnitCount(Integer countyUnitCount) {
        this.countyUnitCount = countyUnitCount;
    }

    public Integer getTownCount() {
        return townCount;
    }

    public void setTownCount(Integer townCount) {
        this.townCount = townCount;
    }
}
