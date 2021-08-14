package com.sheepfly.common.utils;

import org.junit.Test;

import java.util.Arrays;

public class CliUtilTest {
    @Test
    public void testEnum() {
        OPTION_KEY[] values = OPTION_KEY.values();
        Arrays.stream(values).forEach(System.out::println);
        OPTION_KEY opt = OPTION_KEY.valueOf("OPT");
        System.out.println(opt);
    }
}