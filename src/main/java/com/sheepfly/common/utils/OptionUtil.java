package com.sheepfly.common.utils;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private static final Logger log = LoggerFactory.getLogger(OptionUtil.class);
    private static final String MAIN_PACKAGE = "com.sheepfly.common";

    public static Options buildGradle2PomOptions() {
        List<Map<OPTION_KEY, Object>> optionList = new ArrayList<>();
        Map<OPTION_KEY, Object> version = new HashMap<>(4);

        version.put(OPTION_KEY.OPT, "v");
        version.put(OPTION_KEY.LONG_OPT, "version");
        version.put(OPTION_KEY.HAS_ARGS, true);
        version.put(OPTION_KEY.DESCRIPTION, "版本号文件路径");
        optionList.add(version);

        Map<OPTION_KEY, Object> build = new HashMap<>(5);
        build.put(OPTION_KEY.OPT, "b");
        build.put(OPTION_KEY.LONG_OPT, "buildfile");
        build.put(OPTION_KEY.REQUIRED, true);
        build.put(OPTION_KEY.HAS_ARGS, true);
        build.put(OPTION_KEY.DESCRIPTION, "build.gradle文件路径");
        optionList.add(build);

        return CliUtil.buildOptions(optionList);
    }

    /**
     * 创建Options。
     *
     * <p>如果传入的不是{@link OptionUtil#MAIN_PACKAGE}，则抛出异常。
     * </p>
     *
     * @param clazz 主方法所在类。
     *
     * @return 主方法所在类对应的Options。
     */
    public static Options buildOptions(Class clazz) {
        if (!OptionUtil.MAIN_PACKAGE.equals(clazz.getPackage().getName())) {
            throw new IllegalArgumentException("参数错误:" + clazz.getPackage());
        }
        String simpleName = clazz.getSimpleName();
        String optionMethod = "build" +
                simpleName.substring(0, 1).toUpperCase() +
                simpleName.substring(1) + "Options";
        try {
            Method method = OptionUtil.class.getMethod(optionMethod);
            return (Options) method.invoke(OptionUtil.class);
        } catch (NoSuchMethodException e) {
            log.error("方法不存在", e);
        } catch (IllegalAccessException e) {
            log.error("无法访问", e);
        } catch (InvocationTargetException e) {
            log.error("调用失败", e);
        }
        return null;
    }

    /**
     * 输出命令行语法，比如{@code java -version}。
     *
     * @param name 主类名
     * @param options 命令行参数。
     */
    public static void help(String name, Options options) {
        String qualifiedName = MAIN_PACKAGE + "." + name;
        try {
            Class<?> clazz = Class.forName(qualifiedName);
            if (!MAIN_PACKAGE.equals(clazz.getPackage().getName())) {
                throw new IllegalArgumentException("参数错误:" + clazz.getPackage().getName());
            }
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(clazz.getSimpleName(), options, true);
        } catch (ClassNotFoundException e) {
            log.error("不存在的类", e);
        }
    }
}
