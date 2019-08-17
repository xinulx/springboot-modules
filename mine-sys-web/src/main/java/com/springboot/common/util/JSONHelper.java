package com.springboot.common.util;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import java.util.Map;
import java.util.Set;

/**
 * 无比强大的JSONHelper输出类
 */

public class JSONHelper {

    private static SerializerFeature[] features = {SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteDateUseDateFormat, SerializerFeature.QuoteFieldNames};
    private static SerializerFeature[] featuresConvertCN = {SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteDateUseDateFormat, SerializerFeature.QuoteFieldNames, SerializerFeature.BrowserCompatible};

    public JSONHelper() {

    }

    /**
     * 将对象输出为JSON
     *
     * @param obj
     * @return
     */
    public static String toJSON(Object obj) {
        return toJSON(obj, new String[]{}, JSONFilterNamesType.EXCLUDE);
    }

    /**
     * 指定日期格式并输出JSON
     *
     * @param obj
     * @param dataFormat
     * @return
     */
    public static String toJSON(Object obj, String dataFormat) {
        return toJSON(obj, new String[]{}, JSONFilterNamesType.EXCLUDE, dataFormat);
    }

    /**
     * 排除指定属性输出JSON<br/>
     * 注意:只能过滤根级数据的属性
     *
     * @param obj
     * @param filterNames 要排除的属性名称
     * @return
     */
    public static String toJSON(Object obj, final String[] filterNames) {
        return toJSON(obj, filterNames, JSONFilterNamesType.EXCLUDE);
    }

    /**
     * 通过JSONFilterNamesType来决定是排除还是包含指定属性并输出JSON<br/>
     * 注意:只能过滤根级数据的属性
     *
     * @param obj
     * @param filterNames 属性名称
     * @param t           枚举类型 EXCLUDE 排除  INCLUDE 包含
     * @return
     */
    public static String toJSON(Object obj, final String[] filterNames, JSONFilterNamesType t) {
        return toJSON(obj, filterNames, t, "yyyy-MM-dd HH:mm:ss");
    }

    public static String toJSON(Object obj, final String[] filterNames, JSONFilterNamesType t, String dataFormat) {
        return toJSON(obj, filterNames, t, "yyyy-MM-dd HH:mm:ss", false);
    }

    /**
     * 通过JSONFilterNamesType来决定是排除还是包含指定属性,同时指定日期格式并输出JSON<br/>
     * 注意:只能过滤根级数据的属性
     *
     * @param obj
     * @param filterNames 属性名称
     * @param t           枚举类型 EXCLUDE 排除  INCLUDE 包含
     * @param dataFormat  日期格式 默认为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String toJSON(Object obj, final String[] filterNames, JSONFilterNamesType t, String dataFormat, Boolean convertCN) {

        // fastjson 过滤不需要序列化的属性,防止进入死循环
        final boolean isInclude = JSONFilterNamesType.INCLUDE.equals(t);
        PropertyFilter filter = new PropertyFilter() {
            @Override
            public boolean apply(Object source, String name, Object value) {
                if (value instanceof HibernateProxy) {//hibernate代理对象
                    LazyInitializer initializer = ((HibernateProxy) value).getHibernateLazyInitializer();
                    if (initializer.isUninitialized()) {
                        return false;
                    }
                } else if (value instanceof PersistentCollection) {//实体关联集合一对多等
                    PersistentCollection collection = (PersistentCollection) value;
                    if (!collection.wasInitialized()) {
                        return false;
                    }
                    Object val = collection.getValue();
                    if (val == null) {
                        return false;
                    }
                }
                if (filterNames != null && filterNames.length > 0) {
                    for (int i = 0; i < filterNames.length; i++) {
                        // 如果是包含的话,自动加上保留关键字的保留
                        if (isInclude) {
                            if (filterNames[i].equals(name) || (name.equals("status") || name.equals("desc") || name.equals("data")))
                                return isInclude;
                        } else {
                            // 如果字段名称与当前的名称相等,如果是过滤则返回false,不过滤则返回true
                            if (filterNames[i].equals(name) && !(name.equals("status") || name.equals("desc") || name.equals("data"))) {
                                return isInclude;
                            }
                        }
                    }
                }

                return !isInclude;
            }
        };
        return getString(obj, dataFormat, convertCN, filter);
    }

    private static String getString(Object obj, String dataFormat, Boolean convertCN, PropertyFilter filter) {
        SerializeWriter sw = convertCN ? new SerializeWriter(featuresConvertCN) : new SerializeWriter(features);
        JSONSerializer serializer = new JSONSerializer(sw);
        serializer.setDateFormat(dataFormat);
        serializer.getPropertyFilters().add(filter);
        serializer.write(obj);
        return sw.toString();
    }

    public static String toJSON(Object obj, Map<Class<?>, Set<String>> filterMap) {
        return toJSON(obj, filterMap, JSONFilterNamesType.EXCLUDE, "yyyy-MM-dd HH:mm:ss");
    }

    public static String toJSON(Object obj, Map<Class<?>, Set<String>> filterMap, JSONFilterNamesType t) {
        return toJSON(obj, filterMap, t, "yyyy-MM-dd HH:mm:ss");
    }

    public static String toJSON(Object obj, Map<Class<?>, Set<String>> filterMap, JSONFilterNamesType t, String dataFormat) {
        return toJSON(obj, filterMap, t, "yyyy-MM-dd HH:mm:ss", false);
    }

    public static String toJSON(Object obj, Map<Class<?>, Set<String>> filterMap, JSONFilterNamesType t, String dataFormat, boolean convertCN) {

        // fastjson 过滤不需要序列化的属性,防止进入死循环
        final boolean isInclude = JSONFilterNamesType.INCLUDE.equals(t);
        JSONSimplePropertyFilter spf = new JSONSimplePropertyFilter(filterMap, isInclude);
        return getString(obj, dataFormat, convertCN, spf);
    }

    private static String getString(Object obj, String dataFormat, boolean convertCN, JSONSimplePropertyFilter spf) {
        return getString(obj, dataFormat, convertCN, spf);
    }

}
