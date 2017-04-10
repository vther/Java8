package com.vther.java8.time;


import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoField;

public class _03_Java8LocalDateTime {


    @Test
    public void useLocalDate() {
        System.out.println("_03_Java8LocalDateTime.useLocalDate");
        LocalDate localDate = LocalDate.of(2014, 3, 18);
        System.out.println("localDate = " + localDate);


        int year = localDate.getYear(); // 2014
        Month month = localDate.getMonth(); // MARCH
        int day = localDate.getDayOfMonth(); // 18
        DayOfWeek dow = localDate.getDayOfWeek(); // TUESDAY
        int len = localDate.lengthOfMonth(); // 31 (days in March)
        boolean leap = localDate.isLeapYear(); // false (not a leap year)


        int y = localDate.get(ChronoField.YEAR);// 2014
        int m = localDate.get(ChronoField.MONTH_OF_YEAR); // 3
        int d = localDate.get(ChronoField.DAY_OF_MONTH); // 18
    }

    @Test
    public void useLocalTime() {

        System.out.println("_03_Java8LocalDateTime.useLocalTime");
        LocalTime localTime = LocalTime.of(13, 45, 20); // 13:45:20
        System.out.println("localTime = " + localTime);
        int hour = localTime.getHour(); // 13
        int minute = localTime.getMinute(); // 45
        int second = localTime.getSecond(); // 20


    }

    @Test
    public void useLocalDateTime() {
        System.out.println("_03_Java8LocalDateTime.useLocalDateTime");
        LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); // 2014-03-18T13:45
        LocalDateTime dt2 = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        LocalDateTime dt3 = LocalDate.now().atTime(13, 45, 20);
        LocalDateTime dt4 = LocalDate.now().atTime(LocalTime.now());
        LocalDateTime dt5 = LocalTime.now().atDate(LocalDate.now());
        System.out.println(dt1);
    }
}
