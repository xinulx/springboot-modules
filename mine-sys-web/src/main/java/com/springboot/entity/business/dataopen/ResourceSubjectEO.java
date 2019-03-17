/*
 * Powered By wangshibao
 * createtime 2017-10-19 17:17:17
 */

package com.springboot.entity.business.dataopen;

import com.springboot.entity.hibernate.impl.AMockEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author wangshibao
 * @createtime 2017-10-19 17:17:17
 */
@Entity
@Table(name = "data_open_resourcesubject")
public class ResourceSubjectEO extends AMockEntity implements Serializable {

    /**
     * 是否在首页显示
     */
    public static enum showState {
        show, hide;
    }

    public static ResourceSubjectEO getEmptyInstence() {
        ResourceSubjectEO ins = new ResourceSubjectEO();
        //set default value this place
        return ins;
    }

    /**
     * 无参数构造函数
     */
    public ResourceSubjectEO() {
        this.isShow = showState.show.toString();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    /**
     * 领域编码
     */
    @Column(name = "subject_code")
    private String subjectCode;
    /**
     * 领域
     */
    @Column(name = "subject_name")
    private String subjectName;
    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    @Column(name = "is_show")
    private String isShow;

    /**
     * 资源目录数量
     */
    @Transient
    private Long resourceNums;

    /**
     * 序号
     */
    public Long getId() {
        return id;
    }

    /**
     * 序号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 领域编码
     */
    public String getSubjectCode() {
        return subjectCode;
    }

    /**
     * 领域编码
     */
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    /**
     * 领域
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * 领域
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public Long getResourceNums() {
        return resourceNums;
    }

    public void setResourceNums(Long resourceNums) {
        this.resourceNums = resourceNums;
    }
}


