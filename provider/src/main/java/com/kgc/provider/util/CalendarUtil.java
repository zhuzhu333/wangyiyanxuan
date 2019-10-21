package com.kgc.provider.util;



import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/20 20:25
 * @since
 */
public class CalendarUtil {

    public static Date getStartTimeOfDay(long now){
        String tz = "GMT+8";
        TimeZone curTimeZone = TimeZone.getTimeZone(tz);
        Calendar calendar = Calendar.getInstance(curTimeZone);
        calendar.setTimeInMillis(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        Date date=new Date(calendar.getTimeInMillis());
        return date;
    }

    public static Date getEndTimeOfDay(long now,int day){
        String tz = "GMT+8";
        TimeZone curTimeZone = TimeZone.getTimeZone(tz);
        Calendar calendar = Calendar.getInstance(curTimeZone);
        calendar.setTimeInMillis(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH,day+1);
        Date date=new Date(calendar.getTimeInMillis());
        return date;
    }
   /* public static void main(String[] args) {

        System.out.println(getEndTimeOfDay(new Date().getTime(),30));
    }*/
}
