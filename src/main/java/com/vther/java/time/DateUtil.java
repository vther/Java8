package com.vther.java.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
    private static DateUtil single = null;

    private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private static DateUtil sin = new DateUtil();

    public static DateUtil getSingle() {    ///该类唯一的一个public方法
        return sin;
    }

    public String tranDateToString(Date date) {
        return date == null ? null : new SimpleDateFormat(PATTERN).format(date);
    }


    public Date string2Date(String source) throws ParseException {
        return source == null ? null : new SimpleDateFormat(PATTERN).parse(source);
    }
}
