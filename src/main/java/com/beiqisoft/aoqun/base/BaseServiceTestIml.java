package com.beiqisoft.aoqun.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

@Service
public abstract class BaseServiceTestIml<T, R> implements BaseService<T, R> {

	protected List<Predicate> getEntityPredicate(T entity, Root<T> root, CriteriaBuilder criteriaBuilder) {
		Class<? extends Object> clazz = entity.getClass();
		Field[] fields = concat(clazz.getDeclaredFields(), clazz.getSuperclass().getDeclaredFields());
		List<Predicate> list = new ArrayList<Predicate>();
		for (Field field : fields) {
			field.setAccessible(true);// 强制访问Private修饰的字段
			if (field.getName().equals("serialVersionUID")) {
				continue;
			}

			try {
				Object val = field.get(entity);
				String type = field.getType().toString();// 得到此属性的类型

				if (val == null)
					continue;
				
				if (isBaseType(type)) {
					createPredicate(list,null, field.getName(),val, type,root,criteriaBuilder,0);
				} else {
					//one entity
					Class<? extends Object> clazz1 = val.getClass();
					Field[] fields1 = concat(clazz1.getDeclaredFields(), clazz1.getSuperclass().getDeclaredFields());
					for (Field field1 : fields1) {
						field1.setAccessible(true);
						Object val1 = field1.get(val);
						String type1 = field1.getType().toString();
						if (val1 == null)
							continue;
						if (isBaseType(type1)) {
							createPredicate(list,new String[]{field.getName()},field1.getName(), val1, type1,root,criteriaBuilder,1);
						} 
						else {
//							//two entity
							Class<? extends Object> clazz2 = val1.getClass();
							Field[] fields2 = concat(clazz2.getDeclaredFields(),clazz2.getSuperclass().getDeclaredFields());
							for (Field field2 : fields2) {
								field2.setAccessible(true);
								Object val2 = field2.get(val1);
								String type2 = field2.getType().toString();
								if (val == null)
									continue;
								if (isBaseType(type2)) {
									createPredicate(list,new String[]{field.getName(),field1.getName()},field2.getName(), val2, type2,root,criteriaBuilder,2);
								}
								//TODO 可加入第三层
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return list;
	}

	private void createPredicate(List<Predicate> list,String[] fieldParents, String fieldName, Object val, String type, Root<T> root,
			CriteriaBuilder criteriaBuilder,int flag) {
		if(flag==0) {
			addValueToCriteriaBuilder(list, fieldName, val, type, root, criteriaBuilder);
		}else {
			if(flag ==1 && (type.endsWith("String")|| type.endsWith("Long"))) {
				list.add(criteriaBuilder.equal(root.join(fieldParents[0], JoinType.INNER).get(fieldName), val));
				
			}else  if(flag ==2 && (type.endsWith("String")|| type.endsWith("Long"))){
				list.add(criteriaBuilder.equal(root.join(fieldParents[0], JoinType.INNER).get(fieldParents[1]).get(fieldName), val));
			}
		}
	}

	private void addValueToCriteriaBuilder(List<Predicate> list, String fieldName, Object val, String type,
			Root<T> root, CriteriaBuilder criteriaBuilder) {
		if (type.endsWith("String") && !"".equals(val) && val != null) {
			list.add(criteriaBuilder.like(root.get(fieldName).as(String.class), "%" + val + "%"));
		} else if (type.endsWith("Date") && val != null) {
			if ("startDate".equals(fieldName)) {
				list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ctime").as(Date.class), (Date) val));
			} else if ("endDate".equals(fieldName)) {
				list.add(criteriaBuilder.lessThanOrEqualTo(root.get("ctime").as(Date.class), (Date) val));
			}
		} else if ((type.endsWith("int") || type.endsWith("Integer") || type.endsWith("Long") || type.endsWith("long")
				|| type.endsWith("Double")) && val != null && (!fieldName.equals("pageNum"))) {
			list.add(criteriaBuilder.equal(root.get(fieldName).as(String.class), val));
		}
	}

	private boolean isBaseType(String type) {
		List<String> baseTypes = Arrays.asList(
				new String[] { "int", "long", "double", "float", "String", "Date", "Long", "Integer", "Double" });
		for (String base : baseTypes) {
			if (type.endsWith(base)) {
				return true;
			}
		}
		return false;
	}

	private Field[] concat(Field[] first, Field[] second) {
		Field[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
}
