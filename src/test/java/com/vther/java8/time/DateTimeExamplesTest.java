package com.vther.java8.time;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
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

    @Test
    public void useLocalDateAndLocalTimeAndLocalDateTime() {
        System.out.println("DateTimeExamplesTest.useLocalDate");
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


        System.out.println("DateTimeExamplesTest.useLocalTime");
        LocalTime localTime = LocalTime.of(13, 45, 20); // 13:45:20
        System.out.println("localTime = " + localTime);
        int hour = localTime.getHour(); // 13
        int minute = localTime.getMinute(); // 45
        int second = localTime.getSecond(); // 20


        System.out.println("DateTimeExamplesTest.useLocalDateTime");
        LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); // 2014-03-18T13:45
        LocalDateTime dt2 = LocalDateTime.of(localDate, localTime);
        LocalDateTime dt3 = localDate.atTime(13, 45, 20);
        LocalDateTime dt4 = localDate.atTime(localTime);
        LocalDateTime dt5 = localTime.atDate(localDate);
        System.out.println(dt1);
    }

    @Test
    public void transferBetweenLocalDateAndLocalTimeAndLocalDateTime() {

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate date1 = localDateTime.toLocalDate();
        System.out.println("LocalDate.toLocalDate() = " + date1);
        LocalTime time1 = localDateTime.toLocalTime();
        System.out.println("LocalDate.toLocalTime() = " + time1);

        LocalDate localDate = LocalDate.now();
        LocalDateTime atStartOfDay = localDate.atStartOfDay();
        System.out.println("localDateTime atStartOfDay = " + atStartOfDay);


        Instant instant = Instant.ofEpochSecond(44 * 365 * 86400);
        Instant now = Instant.now();


        LocalTime localTime = LocalTime.now();
        Duration d1 = Duration.between(LocalTime.of(13, 45, 10), localTime);
        Duration d2 = Duration.between(instant, now);
        System.out.println(d1.getSeconds());
        System.out.println(d2.getSeconds());

        Duration threeMinutes = Duration.of(3, ChronoUnit.MINUTES);
        System.out.println("threeMinutes = " + threeMinutes);

        JapaneseDate japaneseDate = JapaneseDate.from(localDate);
        System.out.println("japaneseDate = " + japaneseDate);
    }

    @Test
    public void useTemporalAdjuster() {
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

    @Test
    public void useDateFormatter() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(date.format(formatter));


        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter hwsFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss'Z'");
        System.out.println(localDateTime.format(hwsFormatter));

        DateTimeFormatter complexFormatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.YEAR)
                .appendLiteral("-")
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .appendLiteral("-")
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .appendLiteral("T")
                .appendValue(ChronoField.HOUR_OF_DAY, 2)
                .appendLiteral(":")
                .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                .appendLiteral(":")
                .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                .appendLiteral("Z")
                .parseCaseInsensitive()
                .toFormatter();
        System.out.println(localDateTime.format(complexFormatter));
    }

    @Test
    public void testQuestions() {
        // 1, 1000天以后是星期几
        System.out.println("1000天以后是星期几:" + LocalDate.now().plusDays(1000).getDayOfWeek());

        // 2, 获取今天的凌晨时间
        System.out.println("获取今天的凌晨时间:" + LocalDate.now().atStartOfDay());
    }

    @Test
    public void testQuestions2() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy  hh:mm a");

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
        else
            System.out.printf("  (%s standard time will be in effect.)%n",
                    arrivingZone);
    }

    @Test
    public void testQuestions3() {


        // Flight is 10 hours and 50 minutes, or 650 minutes
        ZoneId arrivingZone = ZoneId.of("Europe/Paris");

//        int sec = 60 * 60 * 24;
//
//
//        Instant now = Instant.now();
//        for (int i = 0; i < 365; i++) {
//            now = now.plusSeconds(sec);
//            if (arrivingZone.getRules().isDaylightSavings(now)) {
//                System.out.println(LocalDateTime.ofInstant(now, arrivingZone).toLocalDate());
//                System.out.println(LocalDateTime.ofInstant(now, arrivingZone).toLocalDate().atStartOfDay(arrivingZone));
//            }
//        }
        LocalDate localDate = LocalDate.of(2017,3,25);

//        System.out.println(localDate.atStartOfDay(arrivingZone));
        LocalDateTime localDateTime = LocalDateTime.of(2017,11,25,1,2);
        ZonedDateTime zonedDateTime = localDateTime.atZone(arrivingZone);
        for (int i = 0; i < 720; i++) {
            zonedDateTime = zonedDateTime.plusHours(1);
            System.out.println(zonedDateTime);

        }

    }

}