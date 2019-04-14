package com.springboot.entity.business;


import com.springboot.entity.hibernate.impl.AMockEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cms_system_user")
public class HbUserEO extends AMockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="rid")
    private Long id;

    @Column(name="role_id")
    private Long roleId;

    @Column(name="organ_id")
    private Long organId;

    @Column(name="unit_id")
    private Long unitId;

    @Column(name="order_num")
    private Integer orderNum;

    @Column(name="userid")
    private String userId;

    @Column(name="position")
    private String position;

    @Column(name="phone")
    private String phone;

    @Column(name="email_address")
    private String emailAddress;

    @Column(name="office_phone")
    private String officePhone;

    @Column(name="office_address")
    private String officeAddress;

    @Column(name="username")
    private String userName;

    @Column(name="password")
    private String passWord;

    @Column(name="password_key")
    private String key;

    @Column(name="status")
    private String status;

    @Column(name="gender")
    private String gender;

    public enum Sex {Male, FeMale}

    public enum Status {Normal, Removed, Locked, Disabled}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getOrganId() {
        return organId;
    }

    public void setOrganId(Long organId) {
        this.organId = organId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}