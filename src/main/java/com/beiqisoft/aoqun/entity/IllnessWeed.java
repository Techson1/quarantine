package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 疾病淘汰实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月11日上午12:10:00
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_illnessWeed")
public class IllnessWeed extends BaseEntity{
	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**淘汰原因*/
	@ManyToOne @JoinColumn(name="reason_id")
	private DeathDisposalReason reason;
	/**淘汰父原因*/
	@ManyToOne @JoinColumn(name="fatherReason_id")
	private DeathDisposalReason fatherReason;
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**栋栏*/
	@ManyToOne @JoinColumn(name="paddock_id")
	private Paddock paddock;
	
	/**疾病淘汰日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	@Transient
	private Date dateAssistStart;
	@Transient
	private Date dateAssistEnd;
	/**处理措施*/
	@Size(max=2)
	private String treat;
	/**类型*/
	private String type;
	/***/ 
	private Long operator_id;
	/**淘汰月龄*/
	@Transient
	private String moonAge;
	/**耳号*/
	@Transient
	private String code;
	/**公羊耳号*/
	@Transient
	private String sireCode;
	/**母羊耳号*/
	@Transient
	private String damCode;
	/**出生日期*/
	@Transient
	private String birthDay;
	/**品种名称*/
	@Transient
	private String breedName;
	/**性别*/
	@Transient
	private String sex;
	/**父原因*/
	@Transient
	private String fatherReasonName;
	/**子原因*/
	@Transient
	private String reasonName;
	/**圈舍名称*/
	@Transient
	private String paddockName;
	/**员工名称*/
	@Transient
	private String contactName;
	
	/**
	 * 修改
	 * */
	public IllnessWeed update(IllnessWeed illnessWeed) {
		this.reason=illnessWeed.getReason();
		this.fatherReason=illnessWeed.getFatherReason();
		this.org=illnessWeed.getOrg();
		this.date=illnessWeed.getDate();
		this.treat=illnessWeed.getTreat();
		return this;
	}
}
