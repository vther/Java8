package com.vther.java8.time;


import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class _05_Java8TimeOfInstant {


    @Test
    public void useClock() {
        Clock clock = Clock.systemUTC();
        System.out.println(clock.millis());
        System.out.println(System.currentTimeMillis());

        System.out.println(clock.getZone());
        System.out.println(clock.instant());

    }


    @Test
    public void useInstant() {
        Instant nowInstant = Instant.now();
        System.out.println(nowInstant.getLong(ChronoField.INSTANT_SECONDS));//1491822692
        System.out.println(System.currentTimeMillis());//1491822692090
        System.out.println(nowInstant);//2017-04-10T11:14:10.238Z
    }


    @Test
    public void useDuration() {
        Duration duration = Duration.of(3, ChronoUnit.MINUTES);
        System.out.println("duration = " + duration);


        //  ！！ Duration 不支持LocalDate，支持LocalDateTime
        Duration d1 = Duration.between(LocalTime.of(12, 0, 0), LocalTime.now());
        System.out.println(d1.getSeconds());//19807
        Duration d2 = Duration.between(Instant.now().minus(1L, ChronoUnit.HOURS), Instant.now());
        System.out.println(d2.getSeconds());//3600
    }

    @Test
    public void usePeriod() {
        Period period = Period.of(1, 3, 10);
        System.out.println(period);

        //  ！！ Duration 不支持LocalTime、LocalDateTime，支持LocalDate
        Period p1 = Period.between(LocalDate.now().minus(10L, ChronoUnit.DAYS), LocalDate.now());
        System.out.println(p1.getDays());//10
    }


}
