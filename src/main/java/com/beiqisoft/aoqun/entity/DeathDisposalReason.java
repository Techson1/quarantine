package com.beiqisoft.aoqun.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;

/**
 * 疾病原因实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_death_disposal_reason")
public class DeathDisposalReason extends BaseEntity{
	/**疾病名称*/
	@Size(max=60)
	private String name;
	/**是否可用*/
	@Size(max=2)
	private String flag;
	/**疾病类型（死亡/淘汰）*/
	@Size(max=3)
	private String type;
	/**父疾病*/
	@ManyToOne @JoinColumn(name = "parent_id")
	private DeathDisposalReason parent;
	/**子疾病集合*/
	@OneToMany(mappedBy = "parent",cascade={CascadeType.PERSIST})
	private List<Pvg> children;
	/**记录人*/
	@Size(max=60)
	private String recorder;
	/**临时字段父id*/
	@Transient
	private Long parentId;
	@Transient
	private String moonAge;
	
	public DeathDisposalReason(Long id){
		this.id=id;
	}

	public DeathDisposalReason setFlagReturnThis(String flag) {
		this.flag=flag;
		return this;
	}
}
