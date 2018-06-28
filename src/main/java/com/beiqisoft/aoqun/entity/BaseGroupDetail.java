package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.beiqisoft.aoqun.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 群组明细实体类
 * @author 程哲旭
 * @version 1.0
 * @time  2018年1月27日上午9:10:00
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_baseGroupDetail")
public class BaseGroupDetail extends BaseEntity{
	/**组群*/
	@ManyToOne @JoinColumn(name="base_group")
	private BaseGroup baseGroup;
	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
}
