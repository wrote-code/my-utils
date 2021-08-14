package com.sheepfly.common.utils;

import org.apache.commons.cli.Options;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * optionUtil。
 *
 * <p>主要有三种方法：</p>
 *
 * <ul>
 *     <li>build开头的方法用来创建对应程序的options</li>
 *     <li>parse方法用来解析命令行参数，即main方法参数</li>
 *     <li>showArgs用来输出解析后的参数，会在parse方法中调用</li>
 * </ul>
 *
 * @author sheepfly
 */
public class OptionUtil {

    public static Options buildGradle2PomOption() {
        List<Map<OPTION_KEY, Object>> optionList = new ArrayList<>();
        Map<OPTION_KEY, Object> version = new HashMap<>();

        version.put(OPTION_KEY.OPT, "v");
        version.put(OPTION_KEY.LONG_OPT, "version");
        version.put(OPTION_KEY.HAS_ARGS, true);
        version.put(OPTION_KEY.DESCRIPTION, "版本号文件路径");
        optionList.add(version);

        Map<OPTION_KEY, Object> build = new HashMap<>();
        build.put(OPTION_KEY.OPT, "b");
        build.put(OPTION_KEY.LONG_OPT, "buildfile");
        build.put(OPTION_KEY.REQUIRED, true);
        build.put(OPTION_KEY.HAS_ARGS, true);
        build.put(OPTION_KEY.DESCRIPTION, "build.gradle文件路径");
        optionList.add(build);

        return CliUtil.buildOptions(optionList);
    }
}
