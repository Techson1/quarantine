package com.beiqisoft.aoqun.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类
 * @author 王栋
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
public class Utils {
	
	/**
	 * MD5 加密
	 * @param inStr
	 * @return
	 */
	public static String getMD5(String inStr) {  
        MessageDigest md5 = null;  
        try {  
            md5 = MessageDigest.getInstance("MD5");  
        } catch (Exception e) {  
              
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
   
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
   
        byte[] md5Bytes = md5.digest(byteArray);  
   
        StringBuffer hexValue = new StringBuffer();  
   
        for (int i = 0; i < md5Bytes.length; i++) {  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
   
        return hexValue.toString();  
    } 
	
	/**
	 * 生成随机时间
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Date randomDate(String beginDate, String endDate) {

		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			Date start = format.parse(beginDate);// 构造开始日期

			Date end = format.parse(endDate);// 构造结束日期

			// getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。

			if (start.getTime() >= end.getTime()) {

				return null;

			}

			long date = random(start.getTime(), end.getTime());

			return new Date(date);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	public static long random(long begin, long end) {

		long rtn = begin + (long) (Math.random() * (end - begin));

		// 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值

		if (rtn == begin || rtn == end) {

			return random(begin, end);

		}

		return rtn;

	}
	
	public static boolean isStringInArray(String str, String[] array){
	   for (String val:array){
	       if(str.equals(val)){
	            return true;
	        }
	   }
	   return false;
	}
}
