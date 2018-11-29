package com.springboot.tag.entity;

import com.springboot.entity.hibernate.impl.AMockEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_label")
public class LabelEO extends AMockEntity {
    //主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name="LABEL_NAME")
    private String labelName;

    /*标签配置*/
    @Column(name = "LABEL_CONFIG")
    private String labelConfig;

    /*标签描述*/
    @Column(name = "LABEL_NOTES")
    private String labelNotes;

    @Column(name = "LABEL_TYPE")
    private Integer labelType;

    @Column(name = "IS_PARENT")
    private Long isParent = 0L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getLabelConfig() {
        return labelConfig;
    }

    public void setLabelConfig(String labelConfig) {
        this.labelConfig = labelConfig;
    }

    public String getLabelNotes() {
        return labelNotes;
    }

    public void setLabelNotes(String labelNotes) {
        this.labelNotes = labelNotes;
    }

    public Integer getLabelType() {
        return labelType;
    }

    public void setLabelType(Integer labelType) {
        this.labelType = labelType;
    }

    public Long getIsParent() {
        return isParent;
    }

    public void setIsParent(Long isParent) {
        this.isParent = isParent;
    }
}
