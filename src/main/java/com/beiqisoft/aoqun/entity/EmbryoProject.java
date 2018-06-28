package com.beiqisoft.aoqun.entity;

import java.util.Date;

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
import com.beiqisoft.aoqun.config.SystemM;

/**
 * 胚移项目实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_embryo_project")
public class EmbryoProject extends BaseEntity{
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**品种*/
	@ManyToOne @JoinColumn(name="breed_id")
	private Breed breed;
	/**技术服务团队*/
	@ManyToOne @JoinColumn(name="customer_id")
	private Customer customer;
	/**项目名称*/
	@Size(max=20)
	private String projectName;
	/**负责人*/
	@Size(max=20)
	private String leader;
	/**状态*/
	@Size(max=20)
	private String status=SystemM.PUBLIC_TRUE;
	/**项目类型<BR>鲜胚移植项目:1<BR>冻胚移植项目:2*/
	@Size(max=20)
	private String introduceFlag;
	/**创建时间*/
	private Date createDate;
	/**供体总数*/
	private Integer donorSum;
	/**供体冲胚数*/
	private Integer donorFlushEmbryoSum;
	/**胚胎总数*/
	private Integer totalSum;
	/**可用胚胎数*/
	private Integer usableSum;
	/**鲜胚数*/
	private Integer embryoSum;
	/**冻胚数*/
	private Integer frozenSum;
	/**受体总数*/
	private Integer receptorSum;
	/**移植受体数*/
	private Integer transplanSum;
	
	public EmbryoProject(Long id){
		this.id=id;
	}

	public EmbryoProject setStatusReturnThis(String status) {
		this.status=status;
		return this;
	}
}
