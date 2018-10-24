package com.beiqisoft.aoqun.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期工具类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
public class DateUtils {
	
	public static int moonDay(String date){
		Calendar   cal   =   new   GregorianCalendar();  
	    SimpleDateFormat oSdf = new SimpleDateFormat ("",Locale.ENGLISH);  
	    oSdf.applyPattern("yyyy-MM");  
	    try {  
	        cal.setTime(oSdf.parse(date));  
	    } catch (ParseException e) {  
	        e.printStackTrace();  
	    }  
	    return  cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
	}
	
	/**
	 * 计算月龄
	 * */
	public static String dateToAge(Date date){
		int age=dateSubDate(new Date(),date);
		return ((int)(age/30.4375))+"";
	}
	
	/**
	 * 计算月龄
	 * */
	public static String dateToAge(Date endDate,Date date){
		int age=dateSubDate(endDate,date);
		return ((int)(age/30.4375))+"";
	}
	
	/**
	 * 计算日龄
	 * */
	public static int dateToDayAge(Date date){
		return dateSubDate(new Date(),date);
	}
	
	/**
	 * 日期减日期
	 * @param minuendDate 
	 * 			被减日期
	 * @param subtrahendDate
	 * 			减去日期
	 * @return long
	 * 			天数
	 * */
	public static int dateSubDate(Date minuendDate ,Date subtrahendDate){
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd"); 
		try {
			return (int)(((time.parse(time.format(minuendDate)).getTime()-time.parse(time.format(subtrahendDate)).getTime())/(24*60*60*1000)));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 日期时间减日期时间
	 * @param minuendDate 
	 * 			被减日期
	 * @param subtrahendDate
	 * 			减去日期
	 * @return long
	 * 			天数
	 * */
	public static int dateTimeSubDateTime(Date minuendDate,Date subtrahendDate){
		//SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd"); 
		return (int)((minuendDate.getTime()-subtrahendDate.getTime())/(24*60*60*1000));
	}
	
	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}
	
	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStrMit(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str.substring(0,10);
	}
	
	/**
	 * 时间加减日期
	 * */
	 public static Date dateAddInteger(Date dateTime/*待处理的日期*/,int n/*加减天数*/){ 
	     //日期格式 
	     return new Date(dateTime.getTime() + n * 24 * 60 * 60 * 1000L); 
	  } 
	
    /**
	 * 字符串转换成日期
	 * @param str
	  * @return date
	*/
	 public static Date StrToDate(String str) {
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	     Date date = null;
	     try {
	         date = format.parse(str);
	     } catch (ParseException e) {
	         e.printStackTrace();
	     }
	     return date;
	 }
	 public static Date StrToDate(String str,String frmtStr) {
		 SimpleDateFormat format = new SimpleDateFormat(frmtStr);
	     Date date = null;
	     try {
	         date = format.parse(str);
	     } catch (ParseException e) {
	         e.printStackTrace();
	     }
	     return date;
	 }
	 public static Date StrToDatetime(String str) {
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Date date = null;
	     try {
	         date = format.parse(str);
	     } catch (ParseException e) {
	         e.printStackTrace();
	     }
	     return date;
	 }
	public static String newDate(){
		//我要获取当前的日期
        Date date = new Date();
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取String类型的时间
        String createdate = sdf.format(date);
        return createdate;
	}
	
	/** 
	 * 取得当月天数 
	 * */  
	public static int getCurrentMonthLastDay()  
	{  
	    Calendar a = Calendar.getInstance();  
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
	    int maxDate = a.get(Calendar.DATE);  
	    return maxDate;  
	}  
	  
	/** 
	 * 得到指定月的天数 
	 * */  
	public static int getMonthLastDay(int year, int month)  
	{  
	    Calendar a = Calendar.getInstance();  
	    a.set(Calendar.YEAR, year);  
	    a.set(Calendar.MONTH, month - 1);  
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
	    int maxDate = a.get(Calendar.DATE);  
	    return maxDate;  
	} 
	/**
	 * 格式日期
	 * @param date
	 * @param parentStr
	 * @return
	 */
	public static String getStrDate(Date date,String parentStr) {
		 SimpleDateFormat format = new SimpleDateFormat(parentStr);
		 
		 return format.format(date);
	}
	/**
	 * 将时间戳字符串转化为格式化时间
	 * @param secondes 时间戳字符串
	 * @param format 时间格式
	 * @return 格式化时间
	 */
	public static String timeStamp2Date(String secondes,String format){
		if(secondes==null||secondes.isEmpty()||secondes.equals("null")){
			return "";
		}
		if(format==null||format.isEmpty()){
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(secondes+"000")));
	}
	
}
