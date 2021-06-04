package com.sheepfly.common;

import org.junit.Test;

public class ApplicationTest {
    @Test
    public void f() {
        String s = "12.3.32.44.release";
        System.out.println(s.matches("(\\d*\\.)"));
    }
}
