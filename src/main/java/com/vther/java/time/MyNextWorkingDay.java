package com.vther.java.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.*;


public class MyNextWorkingDay implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {

        DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));

        int dayToAdd = 1;
        if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
        if (dow == DayOfWeek.FRIDAY) {
            // 获取了下个月的第一天
            Temporal firstDayOfNextMonth =LocalDate.from(temporal).with(TemporalAdjusters.firstDayOfNextMonth());
            // 本月的最后一个周六
            LocalDate lastSaturdayOfThisMonth =
                    (LocalDate) firstDayOfNextMonth.minus(firstDayOfNextMonth.get(ChronoField.DAY_OF_WEEK) % 7 + 1, ChronoUnit.DAYS);
            // 明天
            LocalDate tomorrow = (LocalDate) temporal.plus(1L, ChronoUnit.DAYS);
            // 如果明天就是最后一个周六
            if (Period.between(lastSaturdayOfThisMonth, tomorrow).getDays() == 0) {
                dayToAdd = 1;
            } else {
                dayToAdd = 3;
            }
        }
        return temporal.plus(dayToAdd, ChronoUnit.DAYS);
    }
}
