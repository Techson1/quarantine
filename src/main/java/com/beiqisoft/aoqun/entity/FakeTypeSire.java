package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;

/**
 * 公羊缺陷实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_fake_type_sire")
public class FakeTypeSire extends BaseEntity{

	/**公羊缺陷*/
	@Size(max=60)
	private String name;
	/**是否可用*/
	@Size(max=1)
	private String flag;
	/**分场*/
	@ManyToOne @JoinColumn(name = "org_id")
	private Organization org;
	
	public FakeTypeSire setFlagReturnThis(String flag) {
		this.flag=flag;
		return this;
	}
}
