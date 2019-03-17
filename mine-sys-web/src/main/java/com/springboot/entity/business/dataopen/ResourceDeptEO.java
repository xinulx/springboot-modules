/*
 * Powered By wangshibao
 * createtime 2017-10-19 17:17:17
 */

package com.springboot.entity.business.dataopen;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.entity.hibernate.impl.AMockEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 提供机构
 *
 * @author wangshibao
 * @createtime 2017-10-19 17:17:17
 */
@Entity
@Table(name = "data_open_dept")
public class ResourceDeptEO extends AMockEntity implements Serializable {

    /**
     * 是否在首页显示
     */
    public static enum showState {
        show, hide;
    }

    public static ResourceDeptEO getEmptyInstence() {
        ResourceDeptEO ins = new ResourceDeptEO();
        //set default value this place
        ins.setDataNum(0l);
        return ins;
    }

    /**
     * 无参数构造函数
     */
    public ResourceDeptEO() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    /**
     * 部门
     */
    @Column(name = "dept")
    private String dept;
    /**
     * 部门简称
     */
    @Column(name = "dept_simple")
    private String deptSimple;
    /**
     * 前段码
     */
    @Column(name = "pre_code")
    private String preCode;
    /**
     * 数据量
     */
    @Column(name = "data_num")
    private Long dataNum;
    /**
     * 数量更新时间
     */
    @Column(name = "data_Num_Update_Time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date dataNumUpdateTime;

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

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDeptSimple() {
        return deptSimple;
    }

    public void setDeptSimple(String deptSimple) {
        this.deptSimple = deptSimple;
    }

    public String getPreCode() {
        return preCode;
    }

    public void setPreCode(String preCode) {
        this.preCode = preCode;
    }

    public Long getDataNum() {
        if (dataNum == null) {
            return 0l;
        }
        return dataNum;
    }

    public void setDataNum(Long dataNum) {
        this.dataNum = dataNum;
    }

    public Date getDataNumUpdateTime() {
        return dataNumUpdateTime;
    }

    public void setDataNumUpdateTime(Date dataNumUpdateTime) {
        this.dataNumUpdateTime = dataNumUpdateTime;
    }
}


