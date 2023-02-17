package com.finalproject.chatapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:sss'Z'";
    public static String getDate()
    {
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat(FORMAT);
        return ISO_8601_FORMAT.format(new Date());
    }
    public static String getDate(Date date)
    {
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat(FORMAT);
        return ISO_8601_FORMAT.format(date);
    }
    public static String getDate(Date date, String format)
    {
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat(format);
        return ISO_8601_FORMAT.format(date);
    }

    public static String getFormattedDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat(FORMAT);
        try {
            Date date = dateFormat.parse(dateString);
            return getDate(date, "HH:mm - dd.MM.yy");
        } catch (ParseException e) {
            return "";
        }
    }
}
