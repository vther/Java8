package com.vther.java8.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;


public class _08_Java8DateTimeFormatter {

    @Test
    public void useDateFormatter() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(LocalDate.now().format(formatter));


        DateTimeFormatter hwsFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss'Z'");
        System.out.println(LocalDateTime.now().format(hwsFormatter));


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
        System.out.println(LocalDateTime.now().format(complexFormatter));
    }
}
