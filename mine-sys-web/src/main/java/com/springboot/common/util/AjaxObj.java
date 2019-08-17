package com.springboot.common.util;

import java.io.Serializable;

public class AjaxObj implements Serializable {
    private static final long serialVersionUID = -7133144256777652839L;
    private int status;
    private String desc;
    private Object data;

    public AjaxObj() {
    }

    public AjaxObj(int status, String desc, Object data) {
        this.status = status;
        this.desc = desc;
        this.data = data;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static AjaxObj Ok() {
        AjaxObj el = new AjaxObj();
        el.setStatus(1);
        el.setDesc("操作成功");
        el.setData("");
        return el;
    }

    public static AjaxObj Ok(Object data) {
        AjaxObj el = new AjaxObj();
        el.setStatus(1);
        el.setDesc("操作成功");
        el.setData(data);
        return el;
    }

    public static String Ok(Object data, String[] filterNames) {
        AjaxObj el = new AjaxObj();
        el.setStatus(1);
        el.setDesc("操作成功");
        el.setData(data);
        return JSONHelper.toJSON(el, filterNames, JSONFilterNamesType.EXCLUDE);
    }

    public static String Ok(Object data, String[] filterNames, JSONFilterNamesType ft) {
        AjaxObj el = new AjaxObj();
        el.setStatus(1);
        el.setDesc("操作成功");
        el.setData(data);
        return JSONHelper.toJSON(el, filterNames, ft);
    }

    public static AjaxObj Err() {
        AjaxObj el = new AjaxObj();
        el.setStatus(0);
        el.setDesc("操作失败");
        el.setData("");
        return el;
    }

    public static AjaxObj Err(String desc) {
        AjaxObj el = new AjaxObj();
        el.setStatus(0);
        el.setDesc(desc);
        el.setData("");
        return el;
    }

    public static AjaxObj Err(String desc, String data) {
        AjaxObj el = new AjaxObj();
        el.setStatus(0);
        el.setDesc(desc);
        el.setData(data);
        return el;
    }
}
