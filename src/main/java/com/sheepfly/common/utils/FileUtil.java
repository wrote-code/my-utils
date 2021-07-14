package com.sheepfly.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 文件工具
 *
 * @author sheepfly
 */
public class FileUtil {
    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 列出当前文件夹内容。
     *
     * <p>默认情况下，目录放在文件前面。</p>
     *
     * @param dir 要列出内容的目录
     *
     * @return 目录中的内容
     */
    public static String[] list(String dir) {
        Comparator useName = (a, b) -> a.toString().compareTo(b.toString());
        File file = new File(dir);
        String[] list = file.list();
        List<String> dirList = new ArrayList<>();
        List<String> fileList = new ArrayList<>();
        for (String ele : list) {
            if (new File(dir + File.separator + ele).isFile()) {
                fileList.add(ele);
            } else {
                dirList.add(ele);
            }
        }
        dirList.sort(useName);
        fileList.sort(useName);
        dirList.addAll(fileList);
        String[] arr = new String[list.length];
        return dirList.toArray(arr);
    }

    /**
     * 列出文件夹内容时的排序方法。
     *
     * <p>若按文件名排序，则文件夹总是排在文件前面</p>
     *
     * @author sheepfly
     */
    public enum SORT_METHOD {
        NAME("NAME", 1), TYPE("TYPE", 2), SIZE("SIZE", 3);
        private String key;
        private int keyNum;

        SORT_METHOD(String key, int keyNum) {
            this.key = key;
            this.keyNum = keyNum;
        }
    }
}
