package com.sheepfly.common;

import com.sheepfly.common.exceptions.DefaultExceptionHandler;
import com.sheepfly.common.services.Gradle2PomService;
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
 * @author sheepfly
 */
public class Gradle2Pom {
    private static final Logger log = LoggerFactory.getLogger(Gradle2Pom.class);

    public static void main(String[] args) {
        Option version = new Option("v", "version", true,
                "版本号文件路径");
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
            String usage = "Gradle2Pom -b <buildFile> -v [versionFile]";
            helpFormatter.printHelp(usage, options );
            return;
        }
        Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler());
        Gradle2PomService service = new Gradle2PomService();
        service.init(cli);
        service.doService();
    }
}
