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
        System.out.println(LocalDate.of(2017, 4, 7).with(new MyNextWorkingDay()));
        System.out.println(LocalDate.of(2017, 4, 14).with(new MyNextWorkingDay()));
        System.out.println(LocalDate.of(2017, 4, 21).with(new MyNextWorkingDay()));


        System.out.println(LocalDate.of(2017, 4, 27).with(new MyNextWorkingDay()));
        System.out.println(LocalDate.of(2017, 4, 28).with(new MyNextWorkingDay()));
        System.out.println(LocalDate.of(2017, 4, 29).with(new MyNextWorkingDay()));
    }

    @Test
    public void testQuestions3() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss'Z'");

        // Leaving from San Francisco on July 20, 2013, at 7:30 p.m.
        LocalDateTime leaving = LocalDateTime.of(2013, Month.JULY, 20, 19, 30);
        ZoneId leavingZone = ZoneId.of("America/Los_Angeles");
        ZonedDateTime departure = ZonedDateTime.of(leaving, leavingZone);

        try {
            String out1 = departure.format(format);
            System.out.printf("LEAVING:  %s (%s)%n", out1, leavingZone);
        } catch (DateTimeException exc) {
            System.out.printf("%s can't be formatted!%n", departure);
            throw exc;
        }

        // Flight is 10 hours and 50 minutes, or 650 minutes
        ZoneId arrivingZone = ZoneId.of("Asia/Tokyo");
        ZonedDateTime arrival = departure.withZoneSameInstant(arrivingZone)
                .plusMinutes(650);
        try {
            String out2 = arrival.format(format);
            System.out.printf("ARRIVING: %s (%s)%n", out2, arrivingZone);
        } catch (DateTimeException exc) {
            System.out.printf("%s can't be formatted!%n", arrival);
            throw exc;
        }

        if (arrivingZone.getRules().isDaylightSavings(arrival.toInstant()))
            System.out.printf("  (%s daylight saving time will be in effect.)%n",
                    arrivingZone);
        else {
            System.out.printf("  (%s standard time will be in effect.)%n",
                    arrivingZone);
        }
    }


}


