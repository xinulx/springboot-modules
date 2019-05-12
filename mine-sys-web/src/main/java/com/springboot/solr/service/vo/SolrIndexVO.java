package com.springboot.solr.service.vo;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * @author wangshibao
 * @version 2019-05-04 15:33:36
 */
public class SolrIndexVO {

    @Field
    private String id; //唯一标识主键 ：BaseContentEO 的主键 id

    @Field
    private Long columnId;

    @Field
    private String title; //索引标题 ：文章标题

    @Field
    private String remark; //摘要 ：文章摘要

    @Field
    private String content; //内容 ：文章内容

    @Field
    private String typeCode;//类型

    @Field
    private Long siteId; //BaseContentEO 的站点id

    @Field
    private Date createDate;//BaseContentEO 的站点createDate

    @Field
    private String url;

    @Field
    private String author;// 作者

    private String uri;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "SolrIndexVO{" +
                "id='" + id + '\'' +
                ", columnId=" + columnId +
                ", title='" + title + '\'' +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                ", typeCode='" + typeCode + '\'' +
                ", siteId=" + siteId +
                ", createDate=" + createDate +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
