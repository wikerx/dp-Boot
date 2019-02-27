package com.scott.dp.common.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
/**
 * 作者: 薛俊鹏
 * 时间：2018/5/21 on  16:55 .
 * 描述：判断为空的工具类
 * 内容：
 * 状态： 编写/修改
 */

public class Ognl {
    /**
     * 可以用于判断String,Map,Collection,Array是否为空
     *
     * @param o
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object o) throws IllegalArgumentException {
        if (o == null)
            return true;

        if (o instanceof String) {
            if (((String) o).length() == 0) {
                return true;
            }
        } else if (o instanceof Collection) {
            if (((Collection) o).isEmpty()) {
                return true;
            }
        } else if (o.getClass().isArray()) {
            if (Array.getLength(o) == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            if (((Map) o).isEmpty()) {
                return true;
            }
        } else {
            return false;
            // throw new
            // IllegalArgumentException("Illegal argument type,must be : Map,Collection,Array,String. but was:"+o.getClass());
        }

        return false;
    }

    public static boolean mapIsNotEmpty(Map<String, Object> map) {
        return !mapIsEmpty(map);
    }

    public static boolean mapIsEmpty(Map<String, Object> map) {
        boolean isEmpty = true;
        Set<String> keys = map.keySet();
        if (isNotEmpty(keys)) {
            for (String key : keys) {
                Object value = map.get(key);
                if (isNotEmpty(value)) {
                    isEmpty = false;
                    break;
                }
            }
        }
        return isEmpty;
    }

    /**
     * 可以用于判断 Map,Collection,String,Array是否不为空
     *
     * @param o
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isNotBlank(Object o) {
        return !isBlank(o);
    }

    public static boolean isNotEquals(Object o1, Object o2) {
        return !isEquals(o1, o2);
    }

    public static boolean isEquals(Object o1, Object o2) {
        if (isNotEmpty(o1) && isNotEmpty(o2)) {
            return o1.toString().equals(o2.toString());
        }
        return false;
    }

    public static boolean isNumber(Object o) {
        if (o == null)
            return false;
        if (o instanceof Number) {
            return true;
        }
        if (o instanceof String) {
            String str = (String) o;
            if (str.length() == 0)
                return false;
            if (str.trim().length() == 0)
                return false;

            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean isBlank(Object o) {
        if (o == null)
            return true;
        if (o instanceof String) {
            String str = (String) o;
            return isBlank(str);
        }
        return false;
    }

    public static boolean isBlank(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}