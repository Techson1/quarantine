package com.beiqisoft.aoqun.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 杂项工具类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
public class MyUtils {
	
	/**前缀*/
	public static final String PREFIX="000000000000000000000000000000000000000000000000000000000000000000";
	
	public static <T> List<T> listCope(List<T> list){
		List<T> tList =new ArrayList<>();
		for (T t:list){
			tList.add(t);
		}
		return tList;
	}
	
	/**
	 * 根据月龄算天数
	 * */
	public static int moonAgeToDay(int moonAge){
		try{
			return ((int)(moonAge*30.4375))+1;
		}catch(Exception e){
			return 0;
		}
		
	} 
	
	
	public static Double twoDecimal(Double decimal) {
		BigDecimal b = new BigDecimal(decimal);
		Double bb = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		return bb;
	}
	/**
	 * 字符串转换为Long
	 * */
	public static Long strToLong(String number){
		if (number==null || "".equals(number)|| "null".equals(number)){
			return 0L;
		}
		try{
			return Long.parseLong(number);
		}catch(Exception e){
			System.err.println("转换错误");
			return 0L;
		}
	}
	
	/**
	 * 字符串转换为Long
	 * */
	public static int strToInt(String number){
		if (number==null || "".equals(number)|| "null".equals(number)){
			return 0;
		}
		try{
			return Integer.parseInt(number);
		}catch(Exception e){
			System.err.println("转换错误");
			return 0;
		}
	}
	
	/**
	 * 字符串转数值并比较大小
	 * @return 如果第一个参数大于等于第二个参数则返回true否则返回false
	 * */
	public static boolean com(String parameterOne,String parameterTwo){
		try{
			if (Integer.parseInt(parameterOne)>=Integer.parseInt(parameterTwo)){
				return true;
			}
			return false;
		}catch(Exception e){
			return true;
		}
	}
	
	/**
	 * 字符串前补零截取
	 * @param number 参数
	 * @param site 截取未知
	 * @return 补零后的数据
	 * */
	public static String complement(int number,int site){
		return (PREFIX+number).substring((PREFIX+number).length()-site);
	}
	
	/**
	 * list 转换为String[]
	 * */
	public static String[] listToStringArray(List<String> list){
		String[] strs = new String[list.size()];
		return list.toArray(strs);
	}
	
	/**
	 * 计算
	 * */
	public static boolean isStop(int i,int stop){
		if (stop==2){
			return true;
		}
		if((i&1)==stop){ 
	        return true;
	    }   
		return false;
	}
	
	/**
	 * 加一
	 * */
	public static String strParseIntPlusOne(String number){
		try{
			return (1+Integer.parseInt(number))+"";
		}catch(Exception e){
			System.err.println("MyUtils下的strParseIntPlusOne方法错误,错误原因:传输的数据不能转换为int类型");
			return "1";
		}
	}
	
	/**
	 * 减一
	 * */
	public static String strParseIntSubOne(String number){
		try{
			return (-1+Integer.parseInt(number))+"";
		}catch(Exception e){
			System.err.println("MyUtils下的strParseIntPlusOne方法错误,错误原因:传输的数据不能转换为int类型");
			return "1";
		}
	}

	/**
	 * 双精度相加
	 * */
	public static Double doublePlusDouble(Double findByMateria, Double ratio) {
		if (findByMateria==null && ratio==null){
			return 0d;
		}
		if (findByMateria==null){
			return ratio;
		}
		if (ratio==null){
			return findByMateria;
		}
		return ratio+findByMateria;
	}
	
	/**
	 * 字符串已10进制相加并返回字符串
	 * */
	public static String strPlusStr(String materia,String ratio){
		try{
			return Double.parseDouble(materia)+Double.parseDouble(ratio)+"";
		}catch(Exception e){
			System.err.println("进制相加错误");
			return "0";
		}
	}
	/**
	 * 字符串已10进制相减并返回字符串
	 * */
	public static String strSubStr(String materia,String ratio){
		try{
			return Double.parseDouble(materia)-Double.parseDouble(ratio)+"";
		}catch(Exception e){
			System.err.println("进制相加错误");
			return "0";
		}
	}
	
	/**
	 * double减法
	 * */
	public static double strToDoubleSub(String doub1,String boub2){
		try{
			double s= Double.parseDouble(doub1)-Double.parseDouble(boub2);
			return s;
	    }catch(Exception e){
	    	return 0;
	    }
	}
	
	public static double  strToDoble(String doub1){
		try{
			return Double.parseDouble(doub1);
		}catch(Exception e){
			return 0;
		}
	}

	/**
	 * 比较两个字符的大小返回大的一方
	 * */
	public static String strCmpStr(String str1,String str2) {
		return com(str1,str2)?str1:str2;
	}
	
	/**
	 * 计算百分比
	 * */
	public static String percentage(long num1,long num2){
		if (num1==0L || num2==0L){
			return "0.00%";
		}
		try{
			double k = (num1*1.0)/(num2*1.0)*100;
			return String.format("%.2f", k)+"%";
		}catch(Exception e){
			System.out.println("错误");
			return "0.00%";
		}
	}
	
	public static String average(long num1,long num2){
		if (num1==0L || num2==0L){
			return "0.00";
		}
		try{
			double k = (num1*1.0)/(num2*1.0);
			return String.format("%.4f", k);
		}catch(Exception e){
			System.out.println("错误");
			return "0.00";
		}
	}
	
}
