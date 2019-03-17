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
 * 应用信息
 *
 * @author wangshibao
 * @createtime 2017-10-21
 */
@Entity
@Table(name = "data_open_appinfo")
public class AppInfoEO extends AMockEntity implements Serializable {

    /**
     * 媒体类型
     */
    public static enum MediaType {
        APP,
        微信,
        Web;
    }

    public static AppInfoEO getEmptyInstence() {
        AppInfoEO ins = new AppInfoEO();
        //set default value this place
        ins.setMediaType(MediaType.APP.toString());
        ins.setIsPublish(0);
        return ins;
    }

    /**
     * 无参数构造函数
     */
    public AppInfoEO() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    /**
     * 应用名称
     */
    @Column(name = "app_name")
    private String appName;

    /**
     * 媒体类型
     */
    @Column(name = "media_type")
    private String mediaType;

    /**
     * 应用描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 应用类型id
     */
    @Column(name = "app_type_id")
    private Long appTypeId;
    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    private String publishTime;
    /**
     * 是否发布，0：管理员草稿，1：已发布，2：会员草稿，3：审核不通过
     */
    @Column(name = "is_publish")
    private int isPublish;

    @Column(name = "view_times")
    private Long viewTimes;

    @Column(name = "download_times")
    private Long downloadTimes;


    @Column(name = "create_user")
    private String createUser;
    /**
     * 应用图标
     */
    @Column(name = "icon")
    private String icon;
    /**
     * web外链
     */
    @Column(name = "web_url")
    private String webUrl;
    /**
     * 微信二维码
     */
    @Column(name = "wechart_code")
    private String wechartCode;
    /**
     * 应用分类名称
     */
    @Transient
    private String appTypeName;
    /**
     * 驳回原因
     */
    @Column(name = "reject_reason")
    private String rejectReason;

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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAppTypeId() {
        return appTypeId;
    }

    public void setAppTypeId(Long appTypeId) {
        this.appTypeId = appTypeId;
    }

    public String getAppTypeName() {
        return appTypeName;
    }

    public void setAppTypeName(String appTypeName) {
        this.appTypeName = appTypeName;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(int isPublish) {
        this.isPublish = isPublish;
    }

    public Long getViewTimes() {
        return viewTimes;
    }

    public void setViewTimes(Long viewTimes) {
        this.viewTimes = viewTimes;
    }

    public Long getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(Long downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescriptionSimple() {
        return this.description;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getWechartCode() {
        return wechartCode;
    }

    public void setWechartCode(String wechartCode) {
        this.wechartCode = wechartCode;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
/**
 * 过滤所有以"<"开头以">"结尾的标签
 * @param str
 * @return
 */
//	private String filterHtml(String str) {
//		Pattern pattern = Pattern.compile("&lt;([^>]*)&gt;");
//		Matcher matcher = pattern.matcher(str);
//		StringBuffer sb = new StringBuffer();
//		boolean result1 = matcher.find();
//		while (result1) {
//			matcher.appendReplacement(sb, "");
//			result1 = matcher.find();
//		}
//		matcher.appendTail(sb);
//		return sb.toString();
//	}
}


