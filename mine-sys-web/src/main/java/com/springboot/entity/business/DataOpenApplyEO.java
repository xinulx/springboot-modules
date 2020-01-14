package com.springboot.entity.business;

import com.springboot.entity.hibernate.impl.AMockEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数据开放申请信息表
 * @author wangshibao
 * @date 2019-12-10
 */
@Entity
@Table(name = "CMS_DATA_OPEN_APPLY")
public class DataOpenApplyEO extends AMockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    // 数据类型:备用
    @Column(name = "TYPE")
    private String type;
    // 标题
    @Column(name = "title")
    private String title;
    // 申请办理单位Id
    @Column(name = "ORGAN_ID")
    private Long organId;
    // 申请办理单位名称
    @Column(name = "ORGAN_NAME")
    private Long organName;
    // 申请人姓名
    @Column(name = "APPLY_NAME")
    private String name;
    // 联系电话
    @Column(name = "APPLY_PHONE")
    private String phone;
    // 联系地址
    @Column(name = "APPLY_ADDRESS")
    private String address;
    // 内容
    @Column(name = "APPLY_CONTENT")
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getOrganId() {
        return organId;
    }

    public void setOrganId(Long organId) {
        this.organId = organId;
    }

    public Long getOrganName() {
        return organName;
    }

    public void setOrganName(Long organName) {
        this.organName = organName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
