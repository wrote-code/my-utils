package com.sheepfly.common.utils;

import org.apache.commons.cli.Options;
import org.junit.Test;

public class OptionUtilTest {

    @Test
    public void buildGradle2PomOption() {
        Options options = OptionUtil.buildGradle2PomOption();
        System.out.println(options.toString());
    }
}