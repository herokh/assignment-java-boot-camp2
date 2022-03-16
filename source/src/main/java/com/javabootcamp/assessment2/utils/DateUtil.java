package com.javabootcamp.assessment2.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String convertDateToString(Date dt, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        String dateToString = df.format(dt);
        return dateToString;
    }
}
