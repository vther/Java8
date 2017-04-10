package com.vther.java8.time;


import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class _02_UseSimpleDateFormatProperly {

    @Test
    public void useSimpleDateFormatProperly1() {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 30; j++) {
                    try {
                        System.out.println(DateUtil.getSingle().string2Date("2015-12-14T21:06:28Z"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Assert.fail();
                    }
                }
            }).start();
        }
    }

    private static final ThreadLocal<SimpleDateFormat> FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));

    @Test
    public void useSimpleDateFormatProperly2() {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 30; j++) {
                    try {
                        System.out.println(FORMAT.get().parse("2015-12-14T21:06:28Z"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Assert.fail();
                    }
                }
            }).start();
        }
    }
}
