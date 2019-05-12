package com.springboot.entity.business;

import com.springboot.entity.hibernate.impl.AMockEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 栏目信息管理类
 */
@Entity
@Table(name = "CMS_COLUMN_INFO")
public class ColumnEO extends AMockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    // 父栏目ID
    @Column(name = "PARENT_ID")
    private Long parentId;

    // 栏目编码
    @Column(name = "MENU_CODE")
    private String code;

    // 栏目名称
    @Column(name = "MENU_NAME")
    private String name;

    // 栏目跳转地址
    @Column(name = "REDIRECT_LINK")
    private String link;

    // 栏目类型
    @Column(name = "MENU_TYPE")
    private String type;

    // 排序号
    @Column(name = "NUM")
    private Integer num;

    @Transient
    private List<ColumnEO> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<ColumnEO> getChildren() {
        return children;
    }

    public void setChildren(List<ColumnEO> children) {
        this.children = children;
    }
}
