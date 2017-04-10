package com.vther.java8.time;


import org.junit.Test;

import java.time.*;

public class _04_Java8ZoneDateTime {


    @Test
    public void useZonedDateTime() {

        System.out.println("_04_Java8ZoneDateTime.useZonedDateTime");


        ZonedDateTime utc = LocalDateTime.now().atZone(ZoneId.of("UTC"));
        System.out.println(utc);//2017-04-10T19:28:48.438Z[UTC]

        System.out.println(LocalDateTime.now().atZone(ZoneId.of("GMT+8")));//2017-04-10T19:28:48.438+08:00[GMT+08:00]
    }
}
