package com.vther.java8.time;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;


public class _09_Java8Questions {


    @Test
    public void testQuestions() {
        // 1, 1000天以后是星期几
        System.out.println("1000天以后是星期几:" + LocalDate.now().plusDays(1000).getDayOfWeek());

        // 2, 获取今天的凌晨时间
        System.out.println("获取今天的凌晨时间:" + LocalDate.now().atStartOfDay());
    }

    @Test
    public void testQuestions2() {
        // 假设月末周六加班，那么下一个工作日是什么（）
        System.out.println(LocalDate.of(2017, 4, 7).with(new MyNextWorkingDay()));
        System.out.println(LocalDate.of(2017, 4, 14).with(new MyNextWorkingDay()));
        System.out.println(LocalDate.of(2017, 4, 21).with(new MyNextWorkingDay()));
        System.out.println(LocalDate.of(2017, 4, 27).with(new MyNextWorkingDay()));
        System.out.println(LocalDate.of(2017, 4, 28).with(new MyNextWorkingDay()));
        System.out.println(LocalDate.of(2017, 4, 29).with(new MyNextWorkingDay()));
        System.out.println(LocalDate.of(2017, 4, 30).with(new MyNextWorkingDay()));
        System.out.println(LocalDate.of(2017, 5, 1).with(new MyNextWorkingDay()));
    }

    /**
     * 飞机从 San Francisco，在July 20, 2013, at 7:30 p.m出发
     * 经历10小时50分到日本东京
     * 到东京后 东京时间是多少，东京是不是处于夏令时？
     */

    /**
     * 飞机从4月11号，18:25从浦东国际机场
     * --飞往洛杉矶国际机场，全程11h45m
     * -----问几点到洛杉矶？洛杉矶是否是夏令时？
     */
    @Test
    public void testQuestions4() throws DateTimeException {
        // Leaving
        LocalDateTime leaving = LocalDateTime.of(2017, Month.APRIL, 11, 18, 25);
        ZoneId leavingZone = ZoneId.of("Asia/Shanghai");
        ZonedDateTime departure = ZonedDateTime.of(leaving, leavingZone);


        String out1 = departure.format(DateTimeFormatter.ISO_INSTANT);
        System.out.printf("  LEAVING:  %s                                 ----------------UTC:%s%n ", departure, out1);


        // Flight is 11h45m, 705 minutes
        ZoneId arrivingZone = ZoneId.of("America/Los_Angeles");
        ZonedDateTime arrival = departure.withZoneSameInstant(arrivingZone)
                .plusMinutes(705);


        String out2 = arrival.format(DateTimeFormatter.ISO_INSTANT);
        System.out.printf(" ARRIVING: %s                           ----------------UTC:%s%n", arrival, out2);


        if (arrivingZone.getRules().isDaylightSavings(arrival.toInstant())) {
            System.out.printf("  (%s daylight saving time will be in effect.)%n", arrivingZone);
        } else {
            System.out.printf("  (%s standard time will be in effect.)%n", arrivingZone);
        }
    }


}


