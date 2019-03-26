package me.daylight.ktzs.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static String dateToStr(String format,Date date){
        SimpleDateFormat sdf=new SimpleDateFormat(format,Locale.CHINA);
        return sdf.format(date);
    }

    public static Date strToDate(String format,String dateStr){
        SimpleDateFormat sdf=new SimpleDateFormat(format,Locale.CHINA);
        Date date=null;
        try {
            date=sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
