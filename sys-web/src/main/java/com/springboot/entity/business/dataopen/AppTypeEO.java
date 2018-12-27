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
 * 应用类型
 *
 * @author wangshibao
 * @createtime 2017-10-21
 */
@Entity
@Table(name = "data_open_apptype")
public class AppTypeEO extends AMockEntity implements Serializable {

    public static AppTypeEO getEmptyInstence() {
        AppTypeEO ins = new AppTypeEO();
        return ins;
    }

    /**
     * 无参数构造函数
     */
    public AppTypeEO() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    /**
     * 部门
     */
    @Column(name = "type_name")
    private String typeName;
    @Transient
    private String num;

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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}


