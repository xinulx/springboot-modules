package com.springboot.common.util;

import nl.bitwalker.useragentutils.UserAgent;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 工具类<br/>
 * 通用方法，使用频率非常高的方法，如数值类型转换,对象值拷贝
 */
public class AppUtil {

    /**
     * 是否是Ajax请求,如果是ajax请求响应头会有，x-requested-with
     *
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }

    /**
     * response 设置超时
     *
     * @param servletResponse
     */
    public static void setTimeout(ServletResponse servletResponse) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        //在响应头设置session状态
        response.setHeader("session-status", "timeout");
    }

    /**
     * *
     * 获取随机数 日期 + 随机数 （主要用文件名称）
     *
     * @return
     */
    public static String getRandom() {
        Calendar calendar = new GregorianCalendar();
        StringBuilder buffer = new StringBuilder();
        buffer.append(calendar.get(Calendar.YEAR));
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        int millSecond = calendar.get(Calendar.MILLISECOND);
        if (month < 10) {
            buffer.append(0);
        }
        buffer.append(month);
        if (date < 10) {
            buffer.append(0);
        }
        buffer.append(date);
        if (hour < 10) {
            buffer.append(0);
        }
        buffer.append(hour);
        if (min < 10) {
            buffer.append(0);
        }
        buffer.append(min);
        if (sec < 10) {
            buffer.append(0);
        }
        buffer.append(sec);
        if (millSecond < 100) {
            if (millSecond < 10) {
                buffer.append(0);
            }
            buffer.append(0);
        }
        buffer.append(millSecond);
        String random = String.valueOf(Math.random());
        random = random.substring(random.length() - 8, random.length());
        buffer.append(random);
        return buffer.toString();

    }

    /**
     * <p>Discription:[根据request获取前台浏览器标识]</p>
     * Created on 2017年11月20日 下午7:30:08
     *
     * @param request request对象
     * @return String 浏览器标识
     */
    private static String getBrowserInfo(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("UserEO-Agent"));
        String browserInfo = userAgent.getBrowser().toString();
        return browserInfo;
    }

    /**
     * 判断地址是否可用
     *
     * @param urlString
     * @param timeOutMillSeconds
     * @return
     */
    public static boolean checkUrl(String urlString, int timeOutMillSeconds) {
        URL url;
        try {
            url = new URL(urlString);
            URLConnection co = url.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) co;
            httpUrlConnection.setConnectTimeout(timeOutMillSeconds);
            httpUrlConnection.setReadTimeout(timeOutMillSeconds);
            httpUrlConnection.connect();
            String code = new Integer(httpUrlConnection.getResponseCode()).toString();
            String message = httpUrlConnection.getResponseMessage();
            System.out.println("getResponseCode code =" + code);
            System.out.println("getResponseMessage message =" + message);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    /**
     * *
     * 是否为空判断
     *
     * @param o
     * @return
     */
    public static final boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        String s;
        if (!(o instanceof String)) {
            s = o.toString();
        } else {
            s = (String) o;
        }

        if (s == null || s.trim().length() == 0 || "null".equals(s)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是int整数
     *
     * @param sCheck
     * @return
     */
    public static final boolean isInt(String sCheck) {
        if (isEmpty(sCheck)) {
            return false;
        }
        if (sCheck.substring(0, 1).equals("-")) {
            sCheck = sCheck.substring(1);
        }
        if (!(isNumeric(sCheck))) {
            return false;
        }
        return (Long.parseLong(sCheck) <= 2147483647L);
    }

    /**
     * 判断是否是整数
     *
     * @param sCheck
     * @return
     */
    public static final boolean isNumeric(String sCheck) {
        if (sCheck == null) {
            return false;
        }
        if (sCheck.length() == 0) {
            return false;
        }
        String numStr = "0123456789";
        for (int i = 0; i < sCheck.length(); ++i) {
            if (numStr.indexOf(sCheck.charAt(i)) == -1) {
                return false;
            }
        }

        return true;
    }

    /**
     * 将对象转为整型对象
     *
     * @param obj
     * @return
     */
    public static Integer getInteger(Object obj) {
        if (null == obj) {
            return null;
        } else if ((obj instanceof String)) {
            String data = (obj + "").trim();
            if ("".equals(data)) {
                return null;
            } else if (isInt(data)) {
                return Integer.valueOf(data);
            } else {
                return null;
            }
        } else if ((obj instanceof BigDecimal)) {
            return (((BigDecimal) obj).intValue());
        } else if (isInt(obj + "")) {
            return Integer.valueOf(obj + "");
        } else {
            return null;
        }
    }

    /**
     * *
     * 将对象转为短整型
     *
     * @param obj
     * @return
     */
    public static Short getShort(Object obj) {
        if (null == obj) {
            return null;
        } else if ((obj instanceof String)) {
            String data = (obj + "").trim();
            if ("".equals(data)) {
                return null;
            } else if (isInt(data)) {
                return Short.valueOf(data);
            } else {
                return null;
            }
        } else if ((obj instanceof BigDecimal)) {
            return (((BigDecimal) obj).shortValueExact());
        } else if (isInt(obj + "")) {
            return Short.valueOf(obj + "");
        } else {
            return null;
        }
    }

    /**
     * *
     * 将对象转为长整型
     *
     * @param obj
     * @return
     */
    public static Long getLong(Object obj) {
        if (null == obj) {
            return null;
        } else if ((obj instanceof String)) {
            String data = (obj + "").trim();
            if ("".equals(data)) {
                return null;
            } else if (isInt(data)) {
                return Long.valueOf(data);
            } else {
                return null;
            }
        } else if ((obj instanceof BigDecimal)) {
            return new Long(((BigDecimal) obj).longValue());
        } else if (isInt(obj + "")) {
            return Long.valueOf(obj + "");
        } else if (obj instanceof Long) {
            return (Long) (obj);
        } else {
            return null;
        }
    }

    /**
     * *
     * 获取长整型
     *
     * @param obj
     * @return
     */
    public static Double getDouble(Object obj) {
        if (null == obj) {
            return null;
        } else if ((obj instanceof String)) {
            String data = (obj + "").trim();
            if ("".equals(data)) {
                return null;
            } else if (isInt(data)) {
                return Double.valueOf(data);
            } else {
                return null;
            }
        } else if ((obj instanceof BigDecimal)) {
            return new Double(((BigDecimal) obj).longValue());
        } else if (isInt(obj + "")) {
            return Double.valueOf(obj + "");
        } else {
            return null;
        }
    }

    /**
     * 获取Float浮点型
     *
     * @param obj
     * @return
     */
    public static Float getFloat(Object obj) {
        if (null == obj) {
            return null;
        } else if ((obj instanceof String)) {
            String data = (obj + "").trim();
            if ("".equals(data)) {
                return null;
            } else if (isFloat(data)) {
                return Float.valueOf(data);
            } else {
                return null;
            }
        } else if ((obj instanceof BigDecimal)) {
            return new Float(((BigDecimal) obj).longValue());
        } else if (isFloat(obj + "")) {
            return Float.valueOf(obj + "");
        } else {
            return null;
        }
    }

    /**
     * 判断是否是float数字
     *
     * @param sCheck
     * @return
     */
    public static final boolean isFloat(String sCheck) {
        if (isEmpty(sCheck)) {
            return false;
        }
        if (sCheck.indexOf(".") != -1) {
            int dotPos = sCheck.indexOf(".");
            sCheck = sCheck.substring(0, dotPos) + sCheck.substring(dotPos + 1);
        }
        return isNumeric(sCheck);
    }

    /**
     * 将对象转为指定的字符串
     */
    public static String getValue(Object obj) {
        if (null == obj) {
            return "";
        } else if (obj instanceof String) {
            return (String) obj;
        } else {
            return obj + "";
        }
    }

    /**
     * *
     * 属性拷贝，空值不拷贝
     *
     * @param dest
     * @param orig
     */
    public static void copyPropertiesWithoutEmpty(Object dest, Object orig) {
        final BeanWrapper src = new BeanWrapperImpl(orig);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (ObjectUtils.isEmpty(srcValue)) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        org.springframework.beans.BeanUtils.copyProperties(orig, dest, emptyNames.toArray(result));
    }

    /**
     * *
     * 属性拷贝
     *
     * @param dest
     * @param orig
     */
    public static void copyProperties(Object dest, Object orig) {
        org.springframework.beans.BeanUtils.copyProperties(orig, dest);
    }

    /**
     * 属性拷贝,可忽略某些字段
     *
     * @param dest
     * @param orig
     * @param ignoreProperties
     * @author
     */
    public static void copyProperties(Object dest, Object orig, String[] ignoreProperties) {
        org.springframework.beans.BeanUtils.copyProperties(orig, dest, ignoreProperties);
    }

    /**
     * 根据分隔符将字符串转换Long数组
     *
     * @param str       目标字符串
     * @param delimiter 分割符
     * @return
     */
    public static Long[] getLongs(String str, String delimiter) {
        if (null == str || null == delimiter || "".equals(str) || "".equals(delimiter)) {
            return null;
        }
        String strs[] = str.split(delimiter);
        List<Long> tempList = new ArrayList<>();
        Long temp;
        for (int i = 0; i < strs.length; i++) {
            temp = getLong(strs[i]);
            if (null != temp) {
                tempList.add(temp);
            }
        }
        Long[] result = new Long[tempList.size()];
        tempList.toArray(result);
        return result;
    }

    /**
     * 根据分隔符将字符串转换Long数组
     *
     * @param str       目标字符串
     * @param delimiter 分割符
     * @return
     */
    public static String[] getStrings(String str, String delimiter) {
        if (null == str || null == delimiter || "".equals(str) || "".equals(delimiter)) return null;
        return str.split(delimiter);
    }


    /**
     * 获取UUID
     *
     * @return
     * @Time 2014年8月19日 下午7:32:59
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 传入Date类型和需要转换格式返回对应字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatTimeToString(Date date, String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        if (date != null) {
            return f.format(date);
        }
        return null;
    }

    /**
     * 字符串转换时间的方法，失败后返回null
     *
     * @param str
     * @param pattern
     * @return
     * @Time 2014年9月16日 下午3:41:13
     */
    public static Date formatStringToTime(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date result = null;
        try {
            result = format.parse(str);
        } catch (ParseException e) {
            return null;
        }
        return result;
    }

    /**
     * 获取当前年份方法
     *
     * @return
     * @Time 2014年9月5日 上午11:17:10
     */
    public static int getCurrentYear() {
        return getInteger(Calendar.getInstance().getTime().toString().substring(Calendar.getInstance().getTime().toString().length() - 4));
    }

    /**
     * 获取当前时间所在的年份
     *
     * @return
     * @Time 2014年9月13日 下午3:20:02
     */
    public static int getYearByDate(Date date) {
        return getInteger(date.toString().substring(date.toString().length() - 4));
    }

    /**
     * 获取当前时间所在年度的周数
     *
     * @return
     * @Time 2014年9月13日 下午3:08:05
     */
    public static int getCurrentWeekOfYear() {
        return getInteger(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
    }

    /**
     * 获取指定时间所在年度的周数
     *
     * @param date
     * @return
     * @Time 2014年9月13日 下午3:08:30
     */
    public static int getWeekOfYearByDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int weekOfYear = getInteger(c.get(Calendar.WEEK_OF_YEAR));
        //中国化日期修改，星期天作为一周的开始
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            weekOfYear--;
        }
        return weekOfYear;
    }

    /***
     * 判断 参数是否基本类型可基本类型包装类
     * @param setCls
     * @return
     */
    private static boolean isBaseType(Class<?> setCls) {
        //基本类型
        if (setCls.getClass().isPrimitive()) {
            return true;
        } else {
            String getType = setCls.getSimpleName();
            return ("String".equals(getType) ||
                    "Integer".equals(getType) ||
                    "Long".equals(getType) ||
                    "Double".equals(getType) ||
                    "BigDecimal".equals(getType) ||
                    "Float".equals(getType) ||
                    "Boolean".equals(getType) ||
                    "Short".equals(getType) ||
                    "Character".equals(getType) ||
                    "String[]".equals(getType) ||
                    "Integer[]".equals(getType) ||
                    "Long[]".equals(getType) ||
                    "Double[]".equals(getType) ||
                    "BigDecimal[]".equals(getType) ||
                    "Float[]".equals(getType) ||
                    "Boolean[]".equals(getType) ||
                    "Short[]".equals(getType) ||
                    "Character[]".equals(getType)
            );
        }
    }

    /**
     * 强转对应的值类型
     *
     * @param setCls
     * @param value
     * @param dateFormat
     * @return
     */
    public static Object setTypeConversion(Class<?> setCls, Object value, String... dateFormat) {
        if (isEmpty(value)) return null;
        if (setCls.isArray()) return value;
        Object obj = null;
        String getType = setCls.getSimpleName();
        if ("String".equals(getType)) {
            obj = getValue(value);
        } else if ("Integer".equals(getType)) {
            obj = getInteger(value);
        } else if ("Long".equals(getType)) {
            obj = getLong(value);
        } else if ("Double".equals(getType)) {
            obj = getDouble(value);
        } else if ("Float".equals(getType)) {
            obj = getFloat(value);
        } else if ("Boolean".equals(getType)) {
            if (null != value && "1".equals(value + "")) {
                obj = Boolean.TRUE;
            } else if (null != value && "0".equals(value + "")) {
                obj = Boolean.FALSE;
            } else {
                obj = Boolean.valueOf(value + "");
            }
        } else if ("Short".equals(getType)) {
            obj = getShort(value);
        } else if ("Character".equals(getType)) {
            obj = Character.valueOf((value + "").charAt(0));
        } else if ("Date".equals(getType)) {
            if (null != dateFormat && dateFormat.length > 0) {
                for (String str : dateFormat) {
                    try {
                        obj = new SimpleDateFormat(str).parse(value.toString());
                    } catch (Exception e) {
                    }
                }
            }
        } else {
            obj = value;
        }

        return obj;
    }

    /**
     * 将request参数放入Map中
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getRequestParams(HttpServletRequest request) {
        if (null == request) return null;
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> resultMap = new HashMap<String, Object>(map.size());
        String[] strArray;
        for (String key : map.keySet()) {
            strArray = map.get(key);
            if (null != strArray && strArray.length > 0) {
                resultMap.put(key, strArray[0]);
            }
        }
        return resultMap;
    }

    /**
     * 将List转换成Map
     *
     * @param list 需要转换的集合
     * @param key  转换成Map时的Key
     * @return
     */
    public static Map<?, ?> listToMap(List<?> list, String key) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        if (null == list) return map;
        if (isEmpty(key)) return map;
        Object keyValue;
        for (Object obj : list) {
            try {
                key = key.substring(0, 1).toUpperCase() + key.substring(1);
                keyValue = obj.getClass().getMethod("get" + key).invoke(obj);
                map.put(keyValue, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * Solr文档对象转Java对象
     *
     * @param record
     * @param clazz
     * @return Object
     */
    public static Object toBean(SolrDocument record, Class<Object> clazz) {
        Object o = null;
        try {
            o = clazz.newInstance();
        } catch (InstantiationException e) {
            System.out.println("Solr文档对象转Java对象实例化异常:" + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("Solr文档对象转Java对象非法访问异常:" + e.getMessage());
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // log.warn("------------" + record.toString());
            Object value = record.get(field.getName());
            try {
                if (value != null) {
                    org.apache.commons.beanutils.BeanUtils.setProperty(o, field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                System.out.println("Solr文档对象转Java对象方法非法访问异常:" + e.getMessage());
            } catch (InvocationTargetException e) {
                System.out.println("Solr文档对象转Java对象调用目标异常:" + e.getMessage());
            }
        }
        return o;
    }

    public static <T> List<T> toBeanList(SolrDocumentList records, Class clazz) {
        List list = new ArrayList();
        for (SolrDocument record : records) {
            list.add(toBean(record, clazz));
        }
        return list;
    }

    /**
     * 序列化对象
     *
     * @param value
     * @return
     */
    public static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            try {
                if (os != null) os.close();
                if (bos != null) bos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rv;
    }

    /**
     * 反序列化对象
     *
     * @param in
     * @return
     */
    public static Object deserialize(byte[] in) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
                is.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (bis != null) bis.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rv;
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decodeBase64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     */
    public static String encodeBase64(byte[] key) {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

}
