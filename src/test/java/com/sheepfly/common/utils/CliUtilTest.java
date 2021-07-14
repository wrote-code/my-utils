package com.sheepfly.common.utils;

import org.junit.Test;

import java.util.Arrays;

public class CliUtilTest {
    @Test
    public void testEnum() {
        CliUtil.OPTION_KEY[] values = CliUtil.OPTION_KEY.values();
        Arrays.stream(values).forEach(System.out::println);
        CliUtil.OPTION_KEY opt = CliUtil.OPTION_KEY.valueOf("OPT");
        System.out.println(opt);
    }
}