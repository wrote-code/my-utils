package com.sheepfly.common.utils;

/**
 * 参数选项
 *
 * @author sheepfly
 */
public enum OPTION_KEY {
    ARG_NAME("ARG_NAME", 1),
    DESCRIPTION("DESCRIPTION", 2),
    LONG_OPT("LONG_OPT", 3),
    NUMBER_OF_ARGS("NUMBER_OF_ARGS", 4),
    OPT("OPT", 5),
    REQUIRED("REQUIRED", 6),
    TYPE("TYPE", 7),
    HAS_ARGS("HAS_ARGS", 8);

    private String key;
    private int num;

    OPTION_KEY(String key, int num) {
        this.key = key;
        this.num = num;
    }
}
