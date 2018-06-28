package com.beiqisoft.aoqun.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ReflectUtil {
	
	/**
	 * 递归反射
	 * @param o 反射对象
	 * @param classPath 类包名+类名
	 */
	public static void reflectObject(Object o, String classPath) {
		try {
			Class<?> clazz = Class.forName(classPath);// 加载类
			Field[] fields = concat(clazz.getDeclaredFields(), clazz.getSuperclass().getDeclaredFields());
			// 遍历字段集合
			for (Field field : fields) {
				field.setAccessible(true);
				Object val = field.get(o);
				String type = field.getType().toString();// 得到此属性的类型
				if (isBaseType(type)) {
					System.out.println(classPath + ":"+field.getName()+":" + val);
				} else {
					reflectObject(val,val.getClass().getName());//递归反射其他实体
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Field[] concat(Field[] first, Field[] second) {
		Field[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	private static boolean isBaseType(String type) {
		List<String> baseTypes = Arrays
				.asList(new String[] { "int", "long", "double", "float", "String", "Date", "Long", "Integer"});
		for (String base : baseTypes) {
			if (type.endsWith(base)) {
				return true;
			}
		}
		return false;
	}

}
