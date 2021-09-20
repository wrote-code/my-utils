package com.sheepfly.common.utils;

import com.sheepfly.common.Gradle2Pom;
import org.apache.commons.cli.Options;
import org.junit.Test;

public class OptionUtilTest {

    @Test
    public void buildGradle2PomOption() {
        Options options = OptionUtil.buildGradle2PomOptions();
        System.out.println(options);
    }

    @Test
    public void testBuildOptions() {
        Options options = OptionUtil.buildOptions(Gradle2Pom.class);
        System.out.println(options);
    }

    @Test
    public void testHelp() {
        OptionUtil.help("Gradle2Pom", OptionUtil.buildGradle2PomOptions());
    }
}