package com.beiqisoft.aoqun.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;

@Service
public abstract class BaseServiceIml<T,R> implements BaseService<T,R> {

	protected Message message;
	private final String ASSIST_START = "AssistStart";
	private final String ASSIST_END = "AssistEnd";
	private String orderProperty;
	private String orderSort;
	
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

				if (val == null || "".equals(val)) //加入 "".equals(val)
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
								if (val2 == null)
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
		if(val==null || "".equals(val)){
			return ;
		}
		if(flag==0) {
			addValueToCriteriaBuilder(list, fieldName, val, type, root, criteriaBuilder);
		}else {
			if(flag ==1 && (type.endsWith("String")|| type.endsWith("Long")|| type.endsWith("Date"))) {
				if (type.endsWith("Date")){
					if (fieldName.endsWith(ASSIST_START)){
						list.add(criteriaBuilder.greaterThanOrEqualTo(
								root.join(fieldParents[0], JoinType.INNER).get(fieldNameAnalysis(fieldName,"start")),(Date) val));
					}
					if (fieldName.endsWith(ASSIST_END)){
						list.add(criteriaBuilder.lessThanOrEqualTo(
								root.join(fieldParents[0], JoinType.INNER).get(fieldNameAnalysis(fieldName,"end")),(Date) val));
					}
					// if(fieldName.endsWith(ASSIST_START) AND field)
				}
				if (type.endsWith("String")){
					list.add(criteriaBuilder.like(root.join(fieldParents[0], JoinType.INNER).get(fieldName), "%" + val + "%"));
				}
				if (type.endsWith("Long")){
					list.add(criteriaBuilder.equal(root.join(fieldParents[0], JoinType.INNER).get(fieldName), val));
				}
			}else  if(flag ==2 && (type.endsWith("String")|| type.endsWith("Long"))){
				list.add(criteriaBuilder.equal(root.join(fieldParents[0], JoinType.INNER).get(fieldParents[1]).get(fieldName), val));
			}
		}
	}
	
	/**
	 * 获取日期名称
	 * @param fieldName
	 * 			日期名称
	 * @param type
	 * 			截取类型 start开始 end结束
	 * */
	private String fieldNameAnalysis(String fieldName,String type){
		if ("start".equals(type)){
			return fieldName.substring(0,fieldName.length()-ASSIST_START.length());
		}
		else{
			return fieldName.substring(0,fieldName.length()-ASSIST_END.length());
		}
	}

	private void addValueToCriteriaBuilder(List<Predicate> list, String fieldName, Object val, String type,
			Root<T> root, CriteriaBuilder criteriaBuilder) {
		if (type.endsWith("String") && !"".equals(val) && val != null) {
			list.add(criteriaBuilder.like(root.get(fieldName).as(String.class), "%" + val + "%"));
		} else if (type.endsWith("Date") && val != null) {
			if ("startDate".equals(fieldName)) {
			System.err.println(val);
				list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ctime").as(Date.class), (Date) val));
			} else if ("endDate".equals(fieldName)) {
				list.add(criteriaBuilder.lessThanOrEqualTo(root.get("ctime").as(Date.class), (Date) val));
			} else if (fieldName.endsWith(ASSIST_START)){
				list.add(criteriaBuilder.greaterThanOrEqualTo(
						root.get(fieldNameAnalysis(fieldName,"start")).as(Date.class),(Date) val));
			} else if(fieldName.endsWith(ASSIST_END)){
				list.add(criteriaBuilder.lessThanOrEqualTo(
						root.get(fieldNameAnalysis(fieldName,"end")).as(Date.class),(Date) val));
			}
		} else if ((type.endsWith("int") || type.endsWith("Integer") || type.endsWith("Long") || type.endsWith("long")
				|| type.endsWith("Double")) && val != null && (!fieldName.equals("pageNum"))){
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
	
	
	/**
	 * 分页参数
	 * @param page
	 * 			第几页
	 * @param properties
	 * 			排序参数
	 * */
	protected PageRequest pageable(Integer page,String... properties ){
		return new PageRequest(page, GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, properties);
	}
	
	/**
	 * 排序
	 * @param direction
	 * 			排序方式已逗号方式分割
	 * @param property
	 * 			排序字段已逗号方式分割
	 * @return Sort
	 * */
	public Sort sorts(String direction ,String property){
		List<Order> orderSpecs = Collections.emptyList();
		String[] directions=direction.split(",");
		String[] propertys=property.split(",");
		for (int i=0;i<directions.length;++i){
			orderSpecs.add(new Order(sortDirection(directions[i]), propertys[i]));
		}
		return new Sort(orderSpecs);
	}
	
	/**
	 * 排序
	 * @param direction
	 * 			排序方式
	 * @param property...
	 * 			排序字段
	 * @return Sort
	 * */
	public Sort sorts(String direction ,String... property){
		return new Sort(sortDirection(direction), property);
	}
	
	/**
	 * 获取排序方式
	 * */
	private Direction sortDirection(String direction){
		if ("DESC".equals(direction.toUpperCase())){
			return Direction.DESC;
		}
		return Direction.ASC;
	}
	
	/**
	 * 写入排序规则
	 * @param direction
	 * 			排序方式已逗号方式分割
	 * @param property
	 * 			排序字段已逗号方式分割
	 * @return this
	 * */
	public BaseServiceIml<T,R> orders(String direction,String property){
		this.orderSort=direction;
		this.orderProperty=property;
		return this;
	}
	
	/**
	 * 执行排序算法
	 * @param root
	 * 			from子句中的根类型,查询根总是引用实体。
	 * @param criteriaBuilder
	 * 			用于构造标准查询、复合选择、表达式、排序。
	 * @return Order 排序的对象
	 * */
	public List<javax.persistence.criteria.Order> queryOrder(Root<T> root,CriteriaBuilder criteriaBuilder){
		String[] sorts=orderSort.split(",");
		String[] propertys=orderProperty.split(",");
		List<javax.persistence.criteria.Order> orders =new ArrayList<>();
		for (int i=0;i<sorts.length;++i){
			if ("DESC".equals(sorts[i].toUpperCase()))
				orders.add(criteriaBuilder.desc(root.get(propertys[i])));
			else
				orders.add(criteriaBuilder.asc(root.get(propertys[i])));
		}
		return orders;
	}
	
}
