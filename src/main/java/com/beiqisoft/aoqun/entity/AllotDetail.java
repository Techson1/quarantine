package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.config.SystemM;

/**
 * 调拨明细实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年1月27日上午9:10:00
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_allotDetail")
public class AllotDetail extends BaseEntity{
	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**转出饲舍*/
	@ManyToOne @JoinColumn(name="from_paddock_id")
	private Paddock fromPaddock;
	/**转入饲舍*/
	@ManyToOne @JoinColumn(name="to_paddock_id")
	private Paddock toPaddock;
	/**调拨单*/
	@ManyToOne @JoinColumn(name="allot_id")
	private Allot allot;
	/**是否复合<BR>复合:1<BR>未复合:0*/
	@Size(max=2)
	private String flag;
	/**记录人*/
	@Size(max=20)
	private String toRecorder;	
	/**月龄*/
	@Transient
	private String moonAge;
	
	public void add(BaseInfo base,Allot allot) {
		this.base=base;
		this.fromPaddock=base.getPaddock();
		this.flag=SystemM.PUBLIC_FALSE;
		this.allot=allot;
	}
}
