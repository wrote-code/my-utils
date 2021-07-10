package com.sheepfly.common;

import com.sheepfly.common.exceptions.DefaultExceptionHandler;
import com.sheepfly.common.services.interfaces.Service;
import com.sheepfly.common.services.interfaces.impls.Gradle2PomService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 转换gradle依赖为pom。
 *
 * <p>程序运行需要传入build.gradle的路径。在使用时，文件名不限定于build.gradle。文件中的
 * 依赖格式为</p>
 *
 * {@code groupId:artifactId:version}
 *
 * <p>所有满足此格式的字符串都将解析为pom中的依赖，其他格式的字符串不会解析为依赖。若version
 * 是通过版本号变量来替换的，则需要通过 v 参数来指定用来替换version变量的文件。文件扩展名为
 * properties。</p>
 *
 * <p>创建的pom文件会放在和build.gradle通目录的位置。若此目录已经有pom.xml文件，则会覆盖。</p>
 *
 * @author sheepfly
 */
public class Gradle2Pom {
    private static final Logger log = LoggerFactory.getLogger(Gradle2Pom.class);

    public static void main(String[] args) {
        Option version = new Option("v", "version", true, "版本号文件路径");
        Option build = new Option("b", "buildfile", true, "build.gradle文件路径");
        build.setRequired(true);
        Options options = new Options();
        options.addOption(version);
        options.addOption(build);
        CommandLineParser parser = new DefaultParser();
        CommandLine cli;
        HelpFormatter helpFormatter = new HelpFormatter();
        try {
            cli = parser.parse(options, args);
        } catch (ParseException e) {
            log.error("参数错误", e);
            String usage = "Gradle2Pom -b <buildFile> [-v <versionFile>]";
            helpFormatter.printHelp(usage, options);
            return;
        }
        Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler());
        Service service = new Gradle2PomService();
        service.init(cli);
        service.doService();
    }
}
