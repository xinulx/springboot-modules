package com.springboot.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.entity.hibernate.impl.AMockEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 西园新村小学会员
 */
@Entity
@Table(name="CMS_BLOG_MEMBER")
public class BlogMemberEO extends AMockEntity {

	public enum Status {
		Audit(1), // 已审核
		unAudit(0);// 未审核
		private Integer value;
		Status(Integer value){
			this.value=value;
		}
		public Integer getStatus(){
			return value;
		}
	}

	public enum MemberType {
		Teacher(1), // 教师
		Student(2);// 学生
		private Integer value;
		MemberType(Integer value){
			this.value=value;
		}
		public Integer getMemberType(){
			return value;
		}
	}

	public enum Sex {
		Man(1), // 男
		Woman(0);// 女
		private Integer sexNum;
		Sex(Integer sexNum){
			this.sexNum=sexNum;
		}
		public Integer getSex(){
			return sexNum;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "SITE_ID")
	private Long siteId;

	//账户名称
	@Column(name = "UID_")
	private String uid;

	//身份证
	@Column(name = "ID_CARD_IMG")
	private String idCardImg;

	//姓名
	@Column(name = "USER_NAME")
	private String userName;

	//密码
	@Column(name = "PASSWORD")
	private String password;

	//年级：对应栏目ID
	@Column(name = "GRAD_ID")
	private Long gradId;

	//班级：待定
	@Column(name = "CLASS_ID")
	private Integer classId;

	//班主任：对应用户ID
	@Column(name = "CHARG_TEACHER")
	private Long chargTeacher;

	//明文密码
	@Column(name = "PLAIN_PW")
	private String plainPw;

	//性别
	@Column(name = "SEX_")
	private Integer sex = Sex.Man.getSex();

	//邮箱
	@Column(name = "EMAIL")
	private String email;

	//手机号
	@Column(name = "PHONE")
	private String phone;

	//身份证
	@Column(name = "ID_CARD")
	private String idCard;

	//地址
	@Column(name = "ADDRESS")
	private String address;

	//问题
	@Column(name = "QUESTION")
	private String question;

	//答案
	@Column(name = "ANSWER")
	private String answer;

	//最后登录ip
	@Column(name = "IP_")
	private String ip;

	// 登录次数
	@Column(name = "LOGIN_TIMES")
	private Integer loginTimes = 0;

	@Column(name = "LAST_LOGIN_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date lastLoginDate;

	//积分
	@Column(name = "MEMBER_POINTS")
	private Long memberPoints = 0L;

	//角色Id
	@Column(name = "MEMBER_ROLE_ID")
	private Long memberRoleId;

	//会员类型
	@Column(name = "MEMBER_TYPE")
	private Integer memberType = MemberType.Teacher.getMemberType();

	//单位Id
	@Column(name = "UNIT_ID")
	private Long unitId;

	//单位名称
	@Column(name = "UNIT_NAME")
	private String unitName;

	//状态
	@Column(name = "STATUS")
	private Integer status = Status.unAudit.getStatus();

	//登录验证方式 0 否 1 是图片验证码 2短信验证码 3邮箱验证
	@Column(name = "CHECK_METHOD")
	private Integer checkMethod = 1;

	//第三方登录类型
	@Column(name = "OPEN_TYPE")
	private String openType;

	//第三方登录key
	@Column(name = "OPEN_ID")
	private String openId;

	//所属平台
	@Column(name = "PLAT_CODE")
	private String platCode = "西园新村小学";

	/**
	 * 发帖数
	 */
	@Column(name = "POST_COUNT")
	private Long postCount = 0L;

	/**
	 * 回帖数
	 */
	@Column(name = "REPLY_COUNT")
	private Long replyCount = 0L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getIdCardImg() {
		return idCardImg;
	}

	public void setIdCardImg(String idCardImg) {
		this.idCardImg = idCardImg;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getGradId() {
		return gradId;
	}

	public void setGradId(Long gradId) {
		this.gradId = gradId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Long getChargTeacher() {
		return chargTeacher;
	}

	public void setChargTeacher(Long chargTeacher) {
		this.chargTeacher = chargTeacher;
	}

	public String getPlainPw() {
		return plainPw;
	}

	public void setPlainPw(String plainPw) {
		this.plainPw = plainPw;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Long getMemberPoints() {
		return memberPoints;
	}

	public void setMemberPoints(Long memberPoints) {
		this.memberPoints = memberPoints;
	}

	public Long getMemberRoleId() {
		return memberRoleId;
	}

	public void setMemberRoleId(Long memberRoleId) {
		this.memberRoleId = memberRoleId;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCheckMethod() {
		return checkMethod;
	}

	public void setCheckMethod(Integer checkMethod) {
		this.checkMethod = checkMethod;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPlatCode() {
		return platCode;
	}

	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}

	public Long getPostCount() {
		return postCount;
	}

	public void setPostCount(Long postCount) {
		this.postCount = postCount;
	}

	public Long getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Long replyCount) {
		this.replyCount = replyCount;
	}
}
