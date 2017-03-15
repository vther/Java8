package com.vther.java8.time;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTimeExamplesTest {

    @Test
    public void whyUseJava8Time1() {
        Date date = new Date(114, 2, 18);
        System.out.println(date);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.FEBRUARY, 18);
        System.out.println(calendar);
    }

    @Test
    public void whyUseJava8Time2() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 30; j++) {
                    try {
                        System.out.println(format.parse("20170315"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}