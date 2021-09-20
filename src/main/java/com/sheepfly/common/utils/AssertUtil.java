package com.sheepfly.common.utils;

/**
 * 断言工具。
 *
 * @author sheepfly
 */
public class AssertUtil {
    public static void notNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
    }

    public static void notNull(Object obj, String msg) {
        if (obj == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notEmptyString(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("字符串不能为空");
        }
    }

    public static void notEmptyString(String str, String msg) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException(msg);
        }
    }

}
