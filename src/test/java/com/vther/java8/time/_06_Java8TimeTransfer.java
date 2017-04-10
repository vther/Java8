package com.vther.java8.time;


import org.junit.Test;

import java.time.*;

public class _06_Java8TimeTransfer {


    @Test
    public void localDateTime() {

        LocalDate date1 = LocalDateTime.now().toLocalDate();
        System.out.println("LocalDate.toLocalDate() = " + date1);
        LocalTime time1 = LocalDateTime.now().toLocalTime();
        System.out.println("LocalDate.toLocalTime() = " + time1);


        LocalDateTime atStartOfDay = LocalDate.now().atStartOfDay();
        System.out.println("localDateTime atStartOfDay = " + atStartOfDay);

    }

    @Test
    public void instant() {
        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.of("UTC"));
        System.out.println(zonedDateTime);
        System.out.println(zonedDateTime.toInstant());
    }

    @Test
    public void duration() {
        Duration duration = Duration.between(LocalTime.now().minusHours(1L), LocalTime.now());
        System.out.println(LocalTime.now().plus(duration));

        Duration duration2 = Duration.between(LocalDateTime.now().minusHours(1L), LocalDateTime.now());
        System.out.println(LocalTime.now().plus(duration2));
    }

    @Test
    public void period() {
        Period period = Period.between(LocalDate.now().minusDays(1L), LocalDate.now());
        System.out.println(LocalTime.now().plus(period));
    }
}
