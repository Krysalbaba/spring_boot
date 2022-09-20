package com.java.nie.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 * 
 * @author wanggy
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     * 
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    
    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算相差天数
     */
    public static int differentDaysByMillisecond(Date date1, Date date2)
    {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
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


    // 计算两个时间区间中所有月份
    public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }


    /**
     * 获取当前时间前多少分钟
     */

    public static String getLastMinute(Integer minute){

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE,minute);
        return new SimpleDateFormat("yyyyMMddHHmm").format(instance.getTime());
    }




    // 计算两个时间区间中所有小时
    public static List<String> getHourBetween(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH),min.get(Calendar.DAY_OF_MONTH),min.get(Calendar.HOUR_OF_DAY), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH),max.get(Calendar.DAY_OF_MONTH),max.get(Calendar.HOUR_OF_DAY), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(new SimpleDateFormat("yyyyMMddHH").format(curr.getTime()));
            curr.add(Calendar.HOUR, 1);
        }
        return result;
    }

    /**
     * 获取当天0点到现在的时间列表
     * num 当前小时后的 num小时
     * @return
     */
    public static List<String> getHourList(Integer num) throws ParseException {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR,num);
        String localDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(instance.getTime());
        String zeroDate = localDate.substring(0, 10)+ " 00:00:00";
        return getHourBetween(zeroDate, localDate);
    }


    public static String getLastHour(Integer hour){

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR,hour);
        return new SimpleDateFormat("yyyyMMddHH").format(instance.getTime());
    }

    /**
     * 获取近24小时每分钟列表
     * @return
     */
    public static List<String> getLastMinuteList(){
        List<String> result =new ArrayList<>(1440);
        for (int i = 1; i <=1440 ; i++) {
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.MINUTE,-i);
            result.add(new SimpleDateFormat("yyyyMMddHHmm").format(instance.getTime()));
        }
        return result ;
    }

    /**
     * 获取近24小时列表
     * @return
     */
    public static List<String> getLastHourList(){
        List<String> result =new ArrayList<>(24);
        for (int i = 1; i <=24 ; i++) {
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.HOUR,-i);
            result.add(new SimpleDateFormat("yyyyMMddHH").format(instance.getTime()));
        }
        return result ;
    }

    public static List<String> getLastHourList(Integer startTime ,Integer endTime){
        List<String> result =new ArrayList<>(24);
        for (int i = startTime; i < endTime ; i++) {
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.HOUR,-i);
            result.add(new SimpleDateFormat("yyyyMMddHH").format(instance.getTime()));
        }
        return result ;
    }

    /**
     * 获取当天到多少天前的日期列表
     * @param num
     * @return
     */
    public static List<String> getLastDay(Integer num){
        List<String> result =new ArrayList<>();
        for (int i = 0; i < num ; i++) {
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DAY_OF_MONTH,-i);
            result.add(new SimpleDateFormat("yyyyMMdd").format(instance.getTime()));
        }
        return result ;
    }

    /**
     * 获取指定小时内的所有分钟
     */

    public static List<String> getMinuteList(String hour){
        List<String> result =new ArrayList<>() ;
        for (int i = 0; i <60 ; i++) {
            String minute = hour+ String.format("%02d",i);
            result.add(minute);
        }
        return  result ;
    }

    public static void main(String[] args) throws ParseException {
        List<String> hourList = getLastDay(7);
        System.out.println(hourList);
    }

}
