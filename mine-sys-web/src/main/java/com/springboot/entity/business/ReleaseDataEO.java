package com.springboot.entity.business;

import com.springboot.entity.hibernate.impl.ABaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数据发布-数据导入
 * @author wangshibao
 * @date 2018/10/30
 */
@Entity
@Table(name = "CMS_RELEASE_DATA")
public class ReleaseDataEO extends ABaseEntity {

    private static final long serialVersionUID = 5162194941667979527L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="item_name")
    private String itemName; // 数据项名称（指标）

    @Column(name="unit_value")
    private String unitValue; // 计量单位（绝对量）

    @Column(name="growth")
    private String growth; // 增速

    @Column(name="period_date")
    private Integer periodDate; // 统计时间段（年月）

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name="remark")
    private String remark; // 解读

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    public String getGrowth() {
        return growth;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }

    public Integer getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(Integer periodDate) {
        this.periodDate = periodDate;
    }
}
