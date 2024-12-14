package com.dental_flamingos.utils;

import java.time.LocalDateTime;

public class DateFormatFront {
    public static String toTextDate(LocalDateTime localDateTime) {
        String strDate = localDateTime.toString();
        String regex = "T";
        String finalString = strDate.replace(regex, " ");
        return strDate;
    }
}
