package com.vther.java8.time;


import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class _07_Java8TemporalAdjuster {


    @Test
    public void localDateTime() {
        LocalDate date = LocalDate.now();

        // 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星期几要求的日期
        System.out.println(date.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));
        // 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星期几要求的日期，如果该日期已经符合要求，直接返回该对象
        System.out.println(date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)));
        // 创建一个新的日期，它的值为同一个月中，最后一个符合星期几要求的值
        System.out.println(date.with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY)));
        // 创建一个新的日期，它的值为当月的最后一天
        System.out.println(date.with(TemporalAdjusters.lastDayOfMonth()));

        //创建一个新的日期，它的值为同一个月中每一周的第几天
        System.out.println(date.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.SUNDAY)));

        // 自定义Adjuster
        System.out.println(date.with(new NextWorkingDay()));

    }

}
