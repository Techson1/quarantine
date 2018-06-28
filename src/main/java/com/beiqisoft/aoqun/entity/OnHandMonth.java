package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.beiqisoft.aoqun.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "onHandMonth")
public class OnHandMonth extends BaseEntity{
	
	/**出生日期*/
	private String brithday;
	/**期初数量*/
	private Integer startNum;
	/**出生数量*/
	private Integer brithdayNum;
	/**调拨转入数量*/
	private Integer allotInNum;
	/**购入 */
	private Integer purchase;
	/**调拨转出数量*/
	private Integer allotOutNum;
	/**销售数量*/
	private Integer sale;
	/**死亡数量*/
	private Integer death;
	/**疾病淘汰*/
	private Integer disease;
	/**育种淘汰*/
	private Integer breeding;
	/**期末数量*/
	private Integer endNum;
	
	private String sex;
}
