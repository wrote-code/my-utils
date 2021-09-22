package com.sheepfly.common.services.interfaces.impls;

import com.sheepfly.common.utils.AssertUtil;
import com.sheepfly.common.utils.FileUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 遍历目录服务。
 *
 * <p>遍历目录，将目录中的内容输出为树结构。可以指定分隔符</p>
 *
 * @author sheepfly
 */
@Data
@Slf4j
public class TraverseDirectoryServiceImpl extends AbstractDefaultServiceImpl {
    /**
     * 空白字符。
     */
    private static final String WHITESPACE = " ";
    private static final String FILE_PREFIX_FIELD = "filePrefix";
    private static final String DIRECTORY_PREFIX_FIELD = "directoryPrefix";
    private static final String OUTPUT_FILE_FIELD = "outputFile";
    /**
     * 用于在终端输出目录树。
     */
    @SuppressWarnings("java:S106")
    private PrintWriter printWriter = new PrintWriter(System.out);
    /**
     * 文件前缀。
     */
    private String filePrefix = "-";
    /**
     * 目录前缀。多层目录会多次输出前缀。
     */
    private String directoryPrefix = "|";
    /**
     * 要遍历的目录。
     */
    private String workDirectory;
    /**
     * 输出文件。
     */
    private String outputFile;
    /**
     * 输出结果文件。
     */
    private BufferedWriter bfWriter;

    @Override
    public void init(CommandLine cli) {
        super.init(cli);
        if (cli.getOptionValue(DIRECTORY_PREFIX_FIELD) != null) {
            directoryPrefix = cli.getOptionValue(DIRECTORY_PREFIX_FIELD);
        }
        if (cli.getOptionValue(FILE_PREFIX_FIELD) != null) {
            filePrefix = cli.getOptionValue(FILE_PREFIX_FIELD);
        }
        String[] args = cli.getArgs();
        workDirectory = args[0];
        if (cli.getOptionValue(OUTPUT_FILE_FIELD) != null) {
            outputFile = cli.getOptionValue(OUTPUT_FILE_FIELD);
            createResultFile();
        }
        if (args.length != 1) {
            throw new IllegalArgumentException("缺少参数：要遍历目录");
        }
    }

    @Override
    public void init(String[] args) {
        super.init(args);
        if (super.getCli().getOptionValue(DIRECTORY_PREFIX_FIELD) != null) {
            directoryPrefix = super.getCli().getOptionValue(DIRECTORY_PREFIX_FIELD);
        }
        if (super.getCli().getOptionValue(FILE_PREFIX_FIELD) != null) {
            filePrefix = super.getCli().getOptionValue(FILE_PREFIX_FIELD);
        }
        String[] otherArgs = super.getCli().getArgs();
        if (otherArgs.length != 1) {
            throw new IllegalArgumentException("缺少以下参数:要遍历的目录");
        }
        workDirectory = otherArgs[0];
        if (super.getCli().getOptionValue(OUTPUT_FILE_FIELD) != null) {
            outputFile = super.getCli().getOptionValue(OUTPUT_FILE_FIELD);
            createResultFile();
        }
    }

    /**
     * 创建结果文件。
     */
    private void createResultFile() {
        log.info("创建结果文件，文件名:" + outputFile);
        File file = new File(workDirectory + File.separator + outputFile);
        log.info("文件路径:" + file.getAbsolutePath());
        if (file.exists()) {
            log.info("文件已存在，将覆盖原有内容:" + file.getAbsolutePath());
        } else {
            log.info("文件不存在，将创建文件:" + file.getAbsolutePath());
            try {
                boolean result = file.createNewFile();
                log.info("文件创建成功，创建数量:" + result);
            } catch (IOException e) {
                log.error("文件创建失败", e);
            }
        }
        try {
            bfWriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
        } catch (IOException e) {
            log.error("出现异常", e);
        }
    }

    @Override
    public void doService() {
        File file = new File(workDirectory);
        traverse(file, 0);
        AssertUtil.notNull(printWriter);
        printWriter.close();
        AssertUtil.notNull(bfWriter);
        try {
            bfWriter.close();
        } catch (IOException e) {
            log.error("关闭writer失败", e);
        }
    }

    /**
     * 遍历目录。
     *
     * @param path 要遍历的目录。
     * @param currentDeep 当前目录在目录数中的深度。
     */
    public void traverse(File path, int currentDeep) {
        printWriter.write(printPath(path.getName(), currentDeep, true));
        printWriter.write("\r\n");
        printWriter.flush();
        String[] list = FileUtil.list(path.getAbsolutePath());
        for (String ele : list) {
            File file = new File(path + File.separator + ele);
            if (file.isDirectory()) {
                traverse(file, currentDeep + 1);
            } else {
                printWriter.write(printPath(ele, currentDeep + 1, false));
                printWriter.write("\r\n");
                printWriter.flush();
            }
        }
    }

    /**
     * 输出文件或目录。
     *
     * @param name 要输出的文件/目录。
     * @param deep 输出深度。
     * @param b 是否是子目录。
     *
     * @return 路径。
     */
    private String printPath(String name, int deep, boolean b) {
        if (b) {
            return printPath(name + "/", deep);
        } else {
            return printPath(name, deep + 1);
        }
    }

    public String printPath(String name, int deep) {
        String path = indent(deep) + name;
        if (bfWriter != null) {
            try {
                bfWriter.write(path);
                bfWriter.write("\r\n");
            } catch (IOException e) {
                log.error("写入失败", e);
                throw new IllegalStateException("写入失败");
            }
        }
        return path;
    }

    /**
     * 自动缩进。
     *
     * <p>返回indentNum个目录前缀。</p>
     *
     * @param indentNum 要缩进的列数。
     *
     * @return 缩进结果。
     */
    public String indent(int indentNum) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < indentNum; i++) {
            stringBuilder.append(directoryPrefix).append(WHITESPACE);
        }
        stringBuilder.append(filePrefix + WHITESPACE);
        return stringBuilder.toString();
    }
}
