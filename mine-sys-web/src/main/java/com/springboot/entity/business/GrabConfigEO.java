package com.springboot.entity.business;

import com.springboot.entity.hibernate.impl.ABaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cms_grab_config")
public class GrabConfigEO extends ABaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="RID")
    private Long id;

    @Column(name="GRAB_NAME")
    private String name;//名称

    @Column(name="GRAB_URL")
    private String grabUrl;//抓取地址

    @Column(name="NUM")
    private Integer num = 5;//抓取条数

    @Column(name="LIST")
    private String list;//行选择器

    @Column(name="TITLE")
    private String title;//标题选择器

    @Column(name="URL")
    private String url;//链接选择器

    @Column(name="DATE")
    private String date;//日期选择器

    @Column(name="DOMAIN")
    private String domain;//域名

    @Column(name="REPLACE_RULE")
    private String replaceRule;//替换规则，默认用域名替换

    @Column(name="REPLACE_VALUE")
    private String replaceValue;//替换值，默认值为域名

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrabUrl() {
        return grabUrl;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setGrabUrl(String grabUrl) {
        this.grabUrl = grabUrl;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getReplaceRule() {
        return replaceRule;
    }

    public void setReplaceRule(String replaceRule) {
        this.replaceRule = replaceRule;
    }

    public String getReplaceValue() {
        return replaceValue;
    }

    public void setReplaceValue(String replaceValue) {
        this.replaceValue = replaceValue;
    }
}
