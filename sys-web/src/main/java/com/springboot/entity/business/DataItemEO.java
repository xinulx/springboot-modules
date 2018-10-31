package com.springboot.entity.business;

import com.springboot.entity.hibernate.impl.ABaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数据发布-数据项
 * @author wangshibao
 * @date 2018/10/30
 */
@Entity
@Table(name = "CMS_DATA_ITEM")
public class DataItemEO extends ABaseEntity {
    private static final long serialVersionUID = -6184731399595714637L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="parent_id")
    private Long parentId = 0L;// 分类ID

    @Column(name="item_name")
    private String itemName; // 数据项名称

    @Column(name="item_import_name")
    private String itemImportName; // 数据项导入名称

    @Column(name="item_desc")
    private String itemDesc; // 数据项语义解读

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name="item_unit")
    private String itemUnit; // 数据项单位

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

    public String getItemImportName() {
        return itemImportName;
    }

    public void setItemImportName(String itemImportName) {
        this.itemImportName = itemImportName;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }
}
