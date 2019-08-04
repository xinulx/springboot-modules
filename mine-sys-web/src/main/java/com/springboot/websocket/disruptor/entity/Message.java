package com.springboot.websocket.disruptor.entity;

/**
 * 消息实体
 * @author wangshibao
 * @date 2019-08-04 15:39:20
 */
public class Message {

    private String title;//消息标题
    private String body;//消息内容
    private String level;//级别
    private String type = Type.normal.name();// 消息类型
    private Integer status = Status.success.getValue();//状态
    private String className;//类名
    private String timestamp;//时间戳
    private String threadName;//线程名称
    private Long userId;//发送人|接收人
    private Long organId;//发送单位|接收单位

    public Message() {
    }

    public enum Type {
        normal,
        business,
        log,
        fileLog,
        user,
        generateStatic
    }

    public enum Status{
        success(1),
        info(2),
        fail(0),
        warning(3);
        Status(int value){
            this.value = value;
        }

        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganId() {
        return organId;
    }

    public void setOrganId(Long organId) {
        this.organId = organId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
