package com.sheepfly.common.utils;

import org.junit.Test;

import java.io.File;

public class FileUtilTest {
    @Test
    public void testList() {
        String dir = "F:\\Games\\steamapps\\common\\Cities_Skylines\\Tools\\LocalUser\\FtpUser\\temp\\video\\自拍";
        traverse(dir);
    }

    public void traverse(String dir) {
        File file = new File(dir);
        String[] list = FileUtil.list(dir);
        for (String ele : list) {
            File current = new File(dir + File.separator + ele);
            if (current.isDirectory()) {
                traverse(current.getAbsolutePath());
            } else {
                System.out.println(current.getAbsoluteFile());
            }
        }
    }
}