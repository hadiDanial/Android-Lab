package com.finalproject.chatapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static String getDate()
    {
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
        return ISO_8601_FORMAT.format(new Date());
    }
    public static String getDate(Date date)
    {
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
        return ISO_8601_FORMAT.format(date);
    }

}
