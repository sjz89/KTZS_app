package me.daylight.ktzs.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static String dateToStr(String format,Date date){
        SimpleDateFormat sdf=new SimpleDateFormat(format,Locale.CHINA);
        return sdf.format(date);
    }
}
