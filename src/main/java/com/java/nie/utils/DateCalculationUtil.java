package com.java.nie.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间计算工具类
 */
public class DateCalculationUtil {

    public static List<Object> intervalCalculation(String startDate ,String endDate) throws ParseException {
        List<Object> betweenTime = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date a1 = sdf.parse(startDate);
        Date b1 = sdf.parse(endDate);
        Calendar sCalendar = Calendar.getInstance();
        sCalendar.setTime(a1);
        int year = sCalendar.get(Calendar.YEAR);
        int month = sCalendar.get(Calendar.MONTH);
        int day = sCalendar.get(Calendar.DATE);
        sCalendar.set(year, month, day, 0, 0, 0);
        Calendar eCalendar = Calendar.getInstance();
        eCalendar.setTime(b1);
        year = eCalendar.get(Calendar.YEAR);
        month = eCalendar.get(Calendar.MONTH);
        day = eCalendar.get(Calendar.DATE);
        eCalendar.set(year, month, day, 0, 0, 0);
        while (sCalendar.before(eCalendar)){
            betweenTime.add(sdf.format(sCalendar.getTime())) ;
            sCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        betweenTime.add(sdf.format(eCalendar.getTime()));
        return betweenTime ;
    }

    public static String getLatest12Month(Date date){
        Calendar from  =  Calendar.getInstance();
        from.setTime(date);
        from.add(Calendar.MONTH, -11);
        return from.get(Calendar.YEAR)+"-"+fillZero(from.get(Calendar.MONTH)+1 )+"-"+"01";
    }

    /**
     * 格式化月份
     */
    public static String fillZero(int i){
        String month = "";
        if(i<10){
            month = "0" + i;
        }else{
            month = String.valueOf(i);
        }
        return month;
    }
}
