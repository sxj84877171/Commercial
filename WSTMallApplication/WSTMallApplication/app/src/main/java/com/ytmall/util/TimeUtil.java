package com.ytmall.util;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/4/13.
 */
public class TimeUtil {
    public static int getHours(){
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.HOUR_OF_DAY);
    }
    public static String getMinuts(){
        Calendar mCalendar = Calendar.getInstance();
        String minuts;
        if(mCalendar.get(Calendar.MINUTE)<10){
            minuts="0"+mCalendar.get(Calendar.MINUTE);
        }else{
            minuts=String.valueOf(mCalendar.get(Calendar.MINUTE));
        }
        return minuts;
    }
    public static int getIntMinust(){
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.MINUTE);
    }
    public static int getYear(){
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.YEAR);
    }
    public static int getMonth(){
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.MONTH);
    }
    public static int getDate(){
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.DATE);
    }
}
