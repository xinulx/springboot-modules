package com.springboot.common.util;

import com.springboot.common.filter.ShiroUtil;

import java.util.Map;

public abstract class ThreadUtil {

    public static final ThreadLocal<Map<String, Object>> session = new ThreadLocal();

    /**
     * 禁止创建实例
     */
    private ThreadUtil() {
        throw new Error();
    }

    /**
     * 设置缓存map
     * @param map
     */
    public static void set(Map<String, Object> map) {
        if (map != null && map.size() > 0) {
            session.set(map);
        }
    }

    /**
     * 根据key获取session值
     * @param key
     * @return
     */
    public static Object get(ThreadUtil.LocalParamsKey key) {
        if (key != null && !"".equals(key.toString())) {
            Map<String, Object> map = (Map) session.get();
            if (map == null || map.size() == 0) {
                map = (Map<String, Object>) ShiroUtil.getAttribute("threadLocal");
            }
            Object value = null;
            if (map != null && map.size() > 0) {
                value = map.get(key.toString());
            }
            return value;
        } else {
            return null;
        }
    }

    /**
     * 根据key获取session值
     * @param key
     * @return
     */
    public static Long getLong(ThreadUtil.LocalParamsKey key) {
        Object object = get(key);
        return object == null ? null : Long.valueOf(object.toString());
    }

    /**
     * 根据key获取session值
     * @param key
     * @return
     */
    public static Integer getInteger(ThreadUtil.LocalParamsKey key) {
        Object object = get(key);
        return object == null ? null : Integer.valueOf(object.toString());
    }

    /**
     * 根据key获取session值
     * @param key
     * @return
     */
    public static String getString(ThreadUtil.LocalParamsKey key) {
        Object object = get(key);
        return object == null ? null : object.toString();
    }

    /**
     * 枚举key列表
     */
    public enum LocalParamsKey {
        UserId,
        Uid,
        PersonName,
        OrganId,
        OrganName,
        Callback,
        DataFlag,
        IP,
        ConvertCN,
        OP_MenuId,
        OP_ColumnId,
        OP_OrganId,
        OP_CatId, RoleId, UserName;

        LocalParamsKey() {}
    }
}
