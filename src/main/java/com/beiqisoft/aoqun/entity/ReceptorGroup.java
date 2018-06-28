package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;

/**
 * 受体组群实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_receptorGroup")
public class ReceptorGroup extends BaseEntity{
	/**羊只*/
	@OneToOne @JoinColumn(name="base_info_id")
	private BaseInfo baseInfo;
	/**胎次*/
	@OneToOne @JoinColumn(name="parity_id")
	private Parity parity;
	/**项目*/
	@ManyToOne @JoinColumn(name="project_id")
	private EmbryoProject project;
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**是否通过复合*/
	@Size(max=20)
	private String flag;
	public ReceptorGroup setFlagRetuanThis(String flag) {
		this.flag=flag;
		return this;
	}
}
