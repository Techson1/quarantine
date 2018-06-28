package com.beiqisoft.aoqun.entity;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.config.SystemM;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 冻胚库存实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_frozenEmbryo")
public class FrozenEmbryo extends BaseEntity{
	/**冲胚*/
	@ManyToOne @JoinColumn(name="embryo_flush_id")
	private EmbryoFlush embryoFlush;
	/**供体*/
	@ManyToOne @JoinColumn(name="donor_id")
	private BaseInfo donor;
	/**公羊*/
	@ManyToOne @JoinColumn(name="sire_id")
	private BaseInfo sire;
	/**存储罐号*/
	@ManyToOne @JoinColumn(name="frozenStore_id")
	private FrozenStore frozenStore;
	/**项目id*/
	@ManyToOne @JoinColumn(name="project_id")
	private EmbryoProject project;
	/**供应商*/
	@ManyToOne @JoinColumn(name="customer_id")
	private Contact customer;
	/**购入场*/
	@ManyToOne @JoinColumn(name="source_id")
	private Organization source;
	/**胚胎细管编码*/
	@ManyToOne @JoinColumn(name="embryo_registration_id")
	private EmbryoRegistration embryoRegistration;
	/**分厂id*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**冷冻日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date freezeDate;
	/**胚胎细管编号*/
	@Size(max=64)
	private String tubuleCode;
	/**细管颜色*/
	@Size(max=20)
	private String tubuleColor;
	/**套管颜色*/
	@Size(max=20)
	private String drivepipeColor;
	/**档案冻胚数*/
	private Integer frozenNumber;
	/**可用胚胎数*/
	private Integer usableNumber;
	/**实际胚胎数*/
	private Integer realityNumber;
	/**冻胚记录单号*/
	@Size(max=20)
	private String sheetCode;
	/**
	 * 购入来源
	 *  	1：自产
	 *  	2：购入
	 * */
	@Size(max=20)
	private String purchaseFrom;
	/**胚胎等级*/
	@Size(max=20)
	private String embryoLevel;
	/**基因等级*/
	@Size(max=20)
	private String geneticLevel;
	/**
	 * 是否外购
	 *   1:外购
	 *   0:非外购
	 * */
	@Size(max=20)
	private String isOutsourcing=SystemM.PUBLIC_FALSE;
	/**供体母羊*/
	@Size(max=20)
	private String donorCode;
	/**供体公羊*/
	@Size(max=20)
	private String sireCode;
	/**入场日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date enterhDay;
	
	
	/***/
	public FrozenEmbryo(Long id){
		this.id=id;
	}
	
	public boolean isfrozenNumber(Integer frozenNumber){
		if (frozenNumber==null){
			frozenNumber=0;
		}
		if (frozenNumber>this.frozenNumber){
			return true;
		}
		return false;
	}
	
	public FrozenEmbryo setUsableNumberReturnThis(Integer realityNumber) {
		this.realityNumber=realityNumber;
		this.usableNumber=realityNumber;
		return this;
	}

	public FrozenEmbryo setFrozenEmbryo(FrozenEmbryo frozenEmbryo) {
		this.tubuleColor=frozenEmbryo.getTubuleColor();
		this.drivepipeColor=frozenEmbryo.getDrivepipeColor();
		this.frozenNumber=frozenEmbryo.getFrozenNumber();
		this.frozenStore=frozenEmbryo.getFrozenStore();
		return this;
	}

	public FrozenEmbryo formatting() {
		if (this.customer!=null && this.customer.getId()==null){
			this.customer=null;
		}
		if (this.source!=null && this.source.getId()==null){
			this.source=null;
		}
		return this;
	}
}