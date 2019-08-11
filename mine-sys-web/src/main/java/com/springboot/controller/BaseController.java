package com.springboot.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.springboot.common.business.CommonException;
import com.springboot.common.util.AjaxObj;
import com.springboot.common.util.CSRFTokenManager;
import com.springboot.common.util.JSONFilterNamesType;
import com.springboot.common.util.JSONHelper;
import com.springboot.common.util.Jacksons;
import com.springboot.common.util.ThreadUtil;
import com.springboot.entity.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * spring控制器基类
 */
public abstract class BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取返回值-new
     *
     * @return
     */
    public Object getObject() {
        ResultVO result = new ResultVO();
        Object object = null;
        Object tempObj = null;
        Integer dataFlag = ThreadUtil.getInteger(ThreadUtil.LocalParamsKey.DataFlag);
        String callback = ThreadUtil.getString(ThreadUtil.LocalParamsKey.Callback);
        if (dataFlag != null && dataFlag != 0) {
            tempObj = null;
        } else {
            tempObj = result;
        }
        if (!StringUtils.isEmpty(callback)) {
            object = Jacksons.json().fromObjectToJson(new JSONPObject(callback, tempObj));
        } else {
            object = tempObj;
        }
        return object;
    }

    /**
     * 获取返回值-new
     *
     * @param value
     * @return
     */
    public Object getObject(Object value) {
        ResultVO result = new ResultVO();
        if (value != null) {
            result.setData(value);
        }
        Object object = null;
        Object tempObj = null;
        Integer dataFlag = ThreadUtil.getInteger(ThreadUtil.LocalParamsKey.DataFlag);
        String callback = ThreadUtil.getString(ThreadUtil.LocalParamsKey.Callback);
        if (dataFlag != null && dataFlag != 0) {
            tempObj = value;
        } else {
            tempObj = result;
        }
        if (!StringUtils.isEmpty(callback)) {
            object = Jacksons.json().fromObjectToJson(new JSONPObject(callback, tempObj));
        } else {
            object = tempObj;
        }
        return object;
    }

    /**
     * 用于检查jsr303参数校验结果的方法， 如果参数校验失败，自动抛出异常
     *
     * @param result
     * @Time 2014年8月19日 下午5:57:59
     */
    protected void checkBindResult(BindingResult result) {
        if (result != null && !result.getAllErrors().isEmpty()) {
            ObjectError error = result.getAllErrors().get(0);
            throw new CommonException(CommonException.TipsMode.Message.toString(), error.getDefaultMessage());
        }
    }

    // edit by dzl 2014.09.25

    /**
     * 简化的错误输出方法: 参数错误<br/>
     *
     * @return {"status":0,"desc":"参数错误","data":null}
     */
    protected String ajaxParamsErr() {
        return toJSON(AjaxObj.Err("参数错误"));
    }

    /**
     * 简化的错误输出方法: 参数错误
     *
     * @param paramName 参数名称,用于构造错误信息,如: id 则输出显示为:id参数错误
     * @return {"status":0,"desc":"id参数错误","data":null}
     */
    protected String ajaxParamsErr(String paramName) {
        return toJSON(AjaxObj.Err(paramName + "参数错误"));
    }

    /**
     * 简化的错误输出方法: 记录不存在
     *
     * @return {"status":0,"desc":"记录不存在","data":null}
     */
    protected String ajaxNoExistsErr() {
        return toJSON(AjaxObj.Err("记录不存在"));
    }

    /**
     * 输出标准的错误信息对象JSON字符串<br/>
     * 如: {"status":0,"desc":"错误信息","data":null}
     *
     * @param errMsg 错误信息
     * @return {"status":0,"desc":"错误信息","data":null}
     */
    protected String ajaxErr(String errMsg) {
        return toJSON(AjaxObj.Err(errMsg));
    }

    /**
     * 输出标准的错误信息对象JSON字符串<br/>
     *
     * @param errMsg  错误信息
     * @param errCode 错误代码
     * @return {"status":0,"desc":"错误信息","data":错误代码}
     */
    protected String ajaxErr(String errMsg, int errCode) {
        AjaxObj obj = new AjaxObj(0, errMsg, errCode);
        return toJSON(obj);
    }

    /**
     * 输出标准的成功信息对象JSON字符串<br/>
     * 如: {"status":1,"desc":"操作成功","data":null}
     *
     * @return {"status":1,"desc":"操作成功","data":null}
     */
    protected String ajaxOk() {
        return toJSON(AjaxObj.Ok());
    }

    /**
     * 将data数据对象输出标准的成功信息对象JSON字符串<br/>
     *
     * @param data 要输出的数据对象
     * @return {"status":1,"desc":"操作成功","data":data}
     */
    protected String ajaxOk(Object data) {
        return toJSON(AjaxObj.Ok(data));
    }

    /**
     * 排除指定data中的属性输出标准的成功信息对象JSON字符串<br/>
     * 注意:status,desc,data为保留关键字,不会纳入排除行列
     *
     * @param data        要输出的数据对象
     * @param filterNames 要排除的属性名称数据,如new String[]{"createDate"}
     * @return {"status":1,"desc":"操作成功","data":data}
     */
    protected String ajaxOk(Object data, final String[] filterNames) {
        return toJSON(AjaxObj.Ok(data), filterNames);
    }

    /**
     * 指定日期格式并输出标准的成功信息对象JSON字符串<br/>
     *
     * @param data       要输出的数据对象
     * @param dataFormat 如:yyyy-MM-dd yyyy-MM yyyy/MM/dd 等
     * @return {"status":1,"desc":"操作成功","data":data}
     */
    protected String ajaxOk(Object data, final String dataFormat) {
        return toJSON(AjaxObj.Ok(data), dataFormat);
    }

    /**
     * 通过JSONFilterNamesType来决定是排除还是包含指定属性并输出标准的成功信息对象JSON字符串<br/>
     *
     * @param data        要输出的数据对象
     * @param filterNames 属性名称,如new String[]{"createDate"}
     * @param t           JSONFilterNamesType 枚举类型 EXCLUDE 排除  INCLUDE 包含
     * @return {"status":1,"desc":"操作成功","data":data}
     */
    protected String ajaxOk(Object data, final String[] filterNames, JSONFilterNamesType t) {
        return toJSON(AjaxObj.Ok(data), filterNames, t);
    }

    /**
     * 通过JSONFilterNamesType来决定是排除还是包含指定属性,同时指定日期格式并输出标准的成功信息对象JSON字符串<br/>
     *
     * @param data        要输出的数据对象
     * @param filterNames 属性名称
     * @param t           JSONFilterNamesType 枚举类型 EXCLUDE 排除  INCLUDE 包含
     * @param dataFormat  如:yyyy-MM-dd yyyy-MM yyyy/MM/dd 等
     * @return {"status":1,"desc":"操作成功","data":data}
     */
    protected String ajaxOk(Object data, final String[] filterNames, JSONFilterNamesType t, final String dataFormat) {
        return toJSON(AjaxObj.Ok(data), filterNames, t, dataFormat);
    }

    /**
     * 指定排除类对象中的属性<br/>
     * 如://需要过滤或包含的类 + 属性  <br/>
     *
     * @param data      要输出的数据对象
     * @param filterMap 需要过滤或包含的类 + 属性
     * @return {"status":1,"desc":"操作成功","data":data}
     * @code Map<Class < ?>, Set<String>> map = new HashMap<Class<?>, Set<String>>();<br/>
     * Set<String> set=new HashSet<String>();<br/>
     * set.add("isEnable")<br/>
     * map.put(TestEO.class,set)<br/>
     * <br/>
     */
    protected String ajaxOk(Object data, Map<Class<?>, Set<String>> filterMap) {
        return toJSON(AjaxObj.Ok(data), filterMap, JSONFilterNamesType.EXCLUDE, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据指定的JSONFilterNamesType类型决定是排除或包含类对象中的属性<br/>
     *
     * @param data      要输出的数据对象
     * @param filterMap 需要过滤或包含的类 + 属性
     * @param t         JSONFilterNamesType 枚举类型 EXCLUDE 排除  INCLUDE 包含
     * @return {"status":1,"desc":"操作成功","data":data}
     */
    protected String ajaxOk(Object data, Map<Class<?>, Set<String>> filterMap, JSONFilterNamesType t) {
        return toJSON(AjaxObj.Ok(data), filterMap, t, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 通过JSONFilterNamesType来决定是排除还是包含指定属性,同时指定日期格式并输出标准的成功信息对象JSON字符串<br/>
     *
     * @param data       要输出的数据对象
     * @param filterMap  需要过滤或包含的类 + 属性
     * @param t          JSONFilterNamesType 枚举类型 EXCLUDE 排除  INCLUDE 包含
     * @param dataFormat 要输出的日期格式 如:yyyy-MM-dd yyyy-MM yyyy/MM/dd 等
     * @return {"status":1,"desc":"操作成功","data":data}
     */
    protected String ajaxOk(Object data, Map<Class<?>, Set<String>> filterMap, JSONFilterNamesType t, final String dataFormat) {
        return toJSON(AjaxObj.Ok(data), filterMap, t, dataFormat);
    }


    /**
     * 将对象输出为JSON,日期默认为yyyy-MM-dd HH:mm:ss,中文默认为转码状态
     *
     * @param obj
     * @return
     */
    protected String toJSON(AjaxObj obj) {
        return toJSON(obj, new String[]{}, JSONFilterNamesType.EXCLUDE, null);
    }

    /**
     * 指定日期格式并输出JSON
     *
     * @param obj
     * @param dateFormat
     * @return
     */
    protected String toJSON(AjaxObj obj, final String dateFormat) {
        return toJSON(obj, new String[]{}, JSONFilterNamesType.EXCLUDE, dateFormat);
    }

    /**
     * 排除指定属性输出JSON
     *
     * @param obj
     * @param filterNames 要排除的属性名称
     * @return
     */
    protected String toJSON(AjaxObj obj, final String[] filterNames) {
        return toJSON(obj, filterNames, JSONFilterNamesType.EXCLUDE);
    }

    /**
     * 通过JSONFilterNamesType来决定是排除还是包含指定属性并输出JSON
     *
     * @param obj
     * @param filterNames 属性名称
     * @param t           枚举类型 EXCLUDE 排除  INCLUDE 包含
     * @return
     */
    protected String toJSON(AjaxObj obj, final String[] filterNames, JSONFilterNamesType t) {
        return toJSON(obj, filterNames, t, null);
    }

    /**
     * 通过JSONFilterNamesType来决定是排除还是包含指定属性,同时指定日期格式并输出JSON
     *
     * @param obj
     * @param filterNames 属性名称
     * @param t           枚举类型 EXCLUDE 排除  INCLUDE 包含
     * @param dateFormat  日期格式 默认为yyyy-MM-dd HH:mm:ss
     * @return
     */
    protected String toJSON(AjaxObj obj, final String[] filterNames, JSONFilterNamesType t, String dateFormat) {
        Object tempObj = null;
        String returnStr = "";
        Integer dataFlag = ThreadUtil.getInteger(ThreadUtil.LocalParamsKey.DataFlag);
        Integer convertCN = ThreadUtil.getInteger(ThreadUtil.LocalParamsKey.ConvertCN);
        String callback = ThreadUtil.getString(ThreadUtil.LocalParamsKey.Callback);
        if (dateFormat == null || "".equals(dateFormat)) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";
        }
        if (dataFlag != null && dataFlag != 0) {
            tempObj = obj.getData();
        } else {
            tempObj = obj;
        }
        String json = (convertCN != null && convertCN != 0) ? JSONHelper.toJSON(tempObj, filterNames, t, dateFormat, true)
                : JSONHelper.toJSON(tempObj, filterNames, t, dateFormat, false);


        if (!StringUtils.isEmpty(callback)) {
            returnStr = callback + "(" + json + ")";
        } else {
            returnStr = json;
        }
        return returnStr;
    }

    protected String toJSON(AjaxObj obj, Map<Class<?>, Set<String>> filterMap, JSONFilterNamesType t, String dateFormat) {
        Object tempObj = null;
        String returnStr = "";
        Integer dataFlag = ThreadUtil.getInteger(ThreadUtil.LocalParamsKey.DataFlag);
        Integer convertCN = ThreadUtil.getInteger(ThreadUtil.LocalParamsKey.ConvertCN);
        String callback = ThreadUtil.getString(ThreadUtil.LocalParamsKey.Callback);
        if (dateFormat == null || "".equals(dateFormat)) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";
        }
        if (dataFlag != null && dataFlag != 0) {
            tempObj = obj.getData();
        } else {
            tempObj = obj;
        }
        String json = (convertCN != null && convertCN != 0) ? JSONHelper.toJSON(tempObj, filterMap, t, dateFormat, true)
                : JSONHelper.toJSON(tempObj, filterMap, t, dateFormat, false);

        if (!StringUtils.isEmpty(callback)) {
            returnStr = callback + "(" + json + ")";
        } else {
            returnStr = json;
        }
        return returnStr;
    }

    /**
     * 验证ajax提交中的headers中的__RequestVerificationToken值与session中的token是否不致
     * <p>var headers = {};</p>
     * <p>headers['__RequestVerificationToken'] = $("#CSRFToken").val();</p>
     * <p>$.ajax({ type: "POST",headers: headers,cache: false,...});</p>
     *
     * @param request
     * @return
     */
    protected boolean isValidCsrfHeaderToken(HttpServletRequest request) {
        String token1 = request.getHeader("__RequestVerificationToken");
        String token2 = request.getSession().getAttribute(CSRFTokenManager.CSRF_TOKEN4SESSION_ATTRNAME) == null ? null : request.getSession().getAttribute(CSRFTokenManager.CSRF_TOKEN4SESSION_ATTRNAME).toString();
        if (token1 == null || token2 == null || !token1.equals(token2)) {
            return false;
        }
        return true;
    }

}
