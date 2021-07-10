package com.sheepfly.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * 遍历目录。
 *
 * @author sheepfly
 */
public class TraverseDirectory {
    private static String separator = "| ";

    private static String preSeparator = "- ";

    private static OutputStreamWriter osWriter = null;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("请输入路径");
            return;
        }
        String path = args[0];
        File file = new File(path);
        if (file.isFile()) {
            System.out.println("目标路径不是目录:" + path);
            return;
        }
        if (args.length == 2) {
            System.out.println("遍历结果将写入文件:" + args[1]);
            String resultFilePath = args[1];
            File resultFile = new File(path + File.separator + resultFilePath);
            if (!resultFile.exists()) {
                try {
                    resultFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                OutputStream outputStream = new FileOutputStream(resultFilePath);
                osWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        doTraverse(file, 0);
        if (osWriter != null) {
            try {
                osWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void doTraverse(File path, int deep) {
        System.out.println(printPath(path.getName(), deep, true));
        String[] list = path.list();
        for (String ele : list) {
            File file = new File(path + File.separator + ele);
            if (file.isDirectory()) {
                doTraverse(file, deep + 1);
            } else {
                System.out.println(printPath(ele, deep + 1, false));
            }
        }
    }

    public static String indent(int indentNum) {
        String result = "";
        for (int i = 0; i < indentNum; i++) {
            result = result + TraverseDirectory.separator;
        }
        result = result + TraverseDirectory.preSeparator;
        return result;
    }

    public static String printPath(String name, int deep) {
        String path = indent(deep) + name;
        if (osWriter != null) {
            try {
                osWriter.write(path);
                osWriter.write("\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }


    private static String printPath(String name, int deep, boolean b) {
        if (b) {
            return printPath(name + "/", deep);
        } else {
            return printPath(name, deep + 1);
        }
    }
}
