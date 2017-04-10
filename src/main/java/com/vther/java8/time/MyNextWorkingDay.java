package com.vther.java8.time;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.*;


public class MyNextWorkingDay implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {


        DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));

        int dayToAdd = 1;
        if (dow == DayOfWeek.FRIDAY) {
            //获取了下个月的第一天
            Temporal temporal2 = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
            // 获取了要减去
            Temporal temporal3 = temporal.minus(temporal2.get(ChronoField.DAY_OF_WEEK) % 7 + 1, ChronoUnit.DAYS);
            if (Duration.between(temporal3, temporal.plus(1L, ChronoUnit.DAYS)).isZero()) {
                dayToAdd = 1;
            } else {
                dayToAdd = 3;
            }

        }
        if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;


        return temporal.plus(dayToAdd, ChronoUnit.DAYS);
    }
}
