package com.sheepfly.common.utils;

/**
 * 列出文件夹内容时的排序方法。
 *
 * <p>若按文件名排序，则文件夹总是排在文件前面</p>
 *
 * @author sheepfly
 */
public enum SORT_METHOD {
    NAME("NAME", 1),
    TYPE("TYPE", 2),
    SIZE("SIZE", 3);
    private String key;
    private int keyNum;

    SORT_METHOD(String key, int keyNum) {
        this.key = key;
        this.keyNum = keyNum;
    }
}
