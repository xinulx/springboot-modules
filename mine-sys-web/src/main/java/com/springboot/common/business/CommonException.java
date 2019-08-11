package com.springboot.common.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 运行时异常，用户无法处理或不需要处理的异常（包括无法处理或不需要处理的业务异常）
 * 1.自定义继承自BaseRunTimeException的异常
 * 2.直接抛出BaseRunTimeException，并赋与不同的key（在定义key之前需要查看异常约定文档以确保key不重复）
 */
public class CommonException extends RuntimeException {

    protected Logger logger = LoggerFactory.getLogger(CommonException.class);

    /**
     * 用于标识获取异常提示信息的方式
     */
    public enum TipsMode {
        Key,//通过在exceptionTipsMessage中获取
        Message//抛出异常时直接定义
    }

    //提示方式
    private String tipsMode = TipsMode.Key.toString();
    // 异常Key
    private String key;
    //关键字数组，用来替换提示信息中的占位符，按数组的顺序进行替换
    private Object[] keyWords;
    //异常提示信息
    private String tipsMessage;

    private Integer errCode;

    public CommonException() {
        super();
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String key, Object[] keyWords) {
        super();
        this.key = key;
        this.keyWords = keyWords;
    }

    public CommonException(String tipsMode, String value) {
        this(value);
        if (!TipsMode.Key.toString().equals(tipsMode) && !TipsMode.Message.toString().equals(tipsMode)) {
            throw new IllegalArgumentException();
        }
        this.tipsMode = tipsMode;
        if (TipsMode.Key.toString().equals(tipsMode)) {
            this.key = value;
        } else {
            this.tipsMessage = value;
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object[] getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(Object[] keyWords) {
        this.keyWords = keyWords;
    }

    public String getTipsMode() {
        return tipsMode;
    }

    public void setTipsMode(String tipsMode) {
        this.tipsMode = tipsMode;
    }

    public String getTipsMessage() {
        return tipsMessage;
    }

    public void setTipsMessage(String tipsMessage) {
        this.tipsMessage = tipsMessage;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }
}
