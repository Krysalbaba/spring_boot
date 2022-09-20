package com.java.nie.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Q.JI
 * @ClassName: StatUtils
 * @Description: StatUtils
 * @date 2022年7月13日 下午4:10:33
 * @since JDK 1.8
 */

@Slf4j
public class StatUtils {

    /**
     * 将时间戳转换成指定时区时间
     *
     * @param timestampStr 时间戳
     * @param dateFormat   格式 如：yyyy-MM-dd
     * @param offsetFormat 时区偏移（单位：小时） 如：-8
     * @return yyyy-MM-dd
     */
    public static String timestampToDateFormat(Long timestampStr, String dateFormat, Integer offsetFormat) {
        // 出参
        timestampStr = timestampConvert(timestampStr);
        Date date = null;
        try {
            date = new Date(timestampStr);
        } catch (Exception e) {
            log.info("##### 时间转换失败 cause : " + e);
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        // 转换成标准时间
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + offsetFormat);
        Date newDate = calendar.getTime();
        // 获取本地时区
        /*TimeZone timeZone = calendar.getTimeZone();
        sdf.setTimeZone(timeZone);*/

        return sdf.format(newDate);
    }

    /**
     * 将时间戳转换成指定时区时间
     *
     * @param timestampStr 时间戳
     * @param dateFormat   格式 如：yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String timestampToDateFormat(Long timestampStr, String dateFormat) {
        return timestampToDateFormat(timestampStr, dateFormat, 8);
    }


    // 各种时间戳转换为毫秒
    private static Long timestampConvert(Long timestamp) {
        String string = timestamp.toString();
        switch (string.length()) {
            case 10:
                timestamp = timestamp * 1000;
                break;
            case 16:
                timestamp = timestamp / 1000;
                break;
            case 19:
                timestamp = timestamp / 1000 / 1000;
                break;
        }
        return timestamp;
    }


    /**
     * 转字符串
     *
     * @param obj
     * @return
     */
    public static String nvl(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    /**
     * @param obj
     * @return int
     * @Title: toInt
     * @Description: toInt
     */
    public static int toInt(Object obj) {
        Integer rtn = 0;
        try {
            if (null != obj) {
                rtn = Integer.valueOf(obj.toString());
            }
        } catch (Exception ex) {
            rtn = 0;
        }
        return rtn;
    }

    /**
     * @param obj
     * @return double
     * @Title: toDouble
     * @Description: toDouble
     */
    public static double toDouble(Object obj) {
        double rtn = 0d;
        try {
            if (null != obj) {
                rtn = Double.valueOf(obj.toString());
            }
        } catch (Exception ex) {
        }
        return rtn;
    }

    /**
     * @param str
     * @return long
     * @Title: toLong
     * @Description: toLong
     */
    public static long toLong(Object str) {
        Long rtn = 0l;
        try {
            rtn = Long.valueOf(str.toString());
        } catch (Exception ex) {
        }
        return rtn;
    }

    /**
     * @param str
     * @return Float
     * @Title: toFloat
     * @Description: toFloat
     */
    public static Float toFloat(Object str) {
        Float rtn = 0f;
        try {
            rtn = Float.valueOf(str.toString());
        } catch (Exception ex) {
        }
        return rtn;
    }


    /**
     * @param time:
     * @return boolean
     * @author
     * @Description: 当前时间一小时内对比
     */
    public static boolean comparisonHour(long time) {
        return System.currentTimeMillis() - timestampConvert(time) < 3600000;
    }
    
    
    /**
	 * 
	 * add:(提供精确的加法运算). <br/>
	 * 
	 * @param v1
	 * @param v2
	 * @return double 两个参数的和
	 * @since 1.0
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 
	 * subtract:(提供精确的减法运算). <br/>
	 * 
	 * @param v1
	 * @param v2
	 * @return double
	 * @since 1.0
	 */
	public static double subtract(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 
	 * mul:(提供精确的乘法运算，默认保留2位小数). <br/>
	 * 
	 * @param v1
	 * @param v2
	 * @return double 两个参数的积
	 * @since 1.0
	 */
	public static double mul(double v1, double v2) {
		return mul(v1, v2, 2);
	}

	/**
	 * 
	 * @Title: mul @Description: (提供精确的乘法运算，保留n位小数). @param v1 @param v2 @param
	 * n @return @return: double @throws
	 */
	public static double mul(double v1, double v2, int n) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return round(b1.multiply(b2).doubleValue(), n);
	}

	/**
	 * 
	 * div:(精确的除法运算，当发生除不尽的情况时，默认保留两位小数). <br/>
	 * 
	 * @param v1
	 * @param v2
	 * @return double 两个参数的商
	 * @since 1.0
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, 2);
	}

	/**
	 * 
	 * div:(精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入). <br/>
	 * 
	 * @param v1
	 * @param v2
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return double
	 * @since 1.0
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 
	 * @Title: round2
	 * @Description: 四舍五入取整
	 * @param value
	 * @param inum
	 * @return double
	 */
	public static int trunc(double value) {
		BigDecimal big = new BigDecimal(Double.toString(value)).setScale(0, BigDecimal.ROUND_HALF_UP);
		return big.intValue();
	}

	/**
	 * 
	 * round:(保留小数的四舍五入). <br/>
	 * 
	 * @param value
	 * @param inum
	 * @return float
	 * @since 1.0
	 */
	public static float round(float value, int inum) {
		double num = Math.pow(10, inum);
		return (float) (Math.round(value * num) / num);
	}

	/**
	 * 
	 * round:(保留小数的四舍五入). <br/>
	 * 
	 * @param value
	 * @param inum
	 * @return double
	 * @since 1.0
	 */
	public static double round(double value, int inum) {
		double num = Math.pow(10, inum);
		return (double) (Math.round(value * num) / num);
	}
	
	
	 /**
     * 
     * @Title: caclCongestion    
     * @Description: 根据等待时长计算拥堵程度、拥堵指数
     * @param wait 等待时长（秒）
     * @return  
     * Integer[0] 拥堵程度、Integer[1]拥堵指数
     */
    public static Integer[] caclCongestion (Double wait) {
    	Integer congestionDegree = 0;
    	if(wait >= 0 && wait < 55) {
    		congestionDegree = 0;
    	}else if(wait >= 55 && wait < 100){
    		congestionDegree = 1;
    	}else if(wait >= 100 && wait < 145) {
    		congestionDegree = 2;
    	}else {
    		congestionDegree = 3;
    	}
    	
    	Integer congestionIndex = StatUtils.trunc(StatUtils.div(wait, 14.5));
    	if(congestionIndex > 10) {
    		congestionIndex = 10;
    	}
    	
    	return new Integer[] {congestionDegree, congestionIndex};
    }
	
    /**
     * 
     * @Title: heading2Direction    
     * @Description: 航向角转方位
     * @param heading
     * @return  
     * Integer
     */
	public static Integer heading2Direction(Integer heading){
        if (heading == null) {
            return null;
        }        
        return (StatUtils.trunc(StatUtils.div(heading - 225000, 450000, 0)) + 1 + 4) % 8 ;
	}
	
	
	public static void main(String[] args) {
		System.out.println(heading2Direction(0));
	}
}
