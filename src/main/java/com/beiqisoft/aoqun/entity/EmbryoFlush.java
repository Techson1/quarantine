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
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 冲胚
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_embryoFlush")
public class EmbryoFlush extends BaseEntity{
	/**供体*/
	@ManyToOne @JoinColumn(name="donor_id")
	private BaseInfo donor;
	/**公羊*/
	@ManyToOne @JoinColumn(name="sire_id")
	private BaseInfo sire;
	private String flushFlag;
	/**项目*/
	@ManyToOne @JoinColumn(name="project_id")
	private EmbryoProject project;
	/**配种记录AI*/
	@ManyToOne @JoinColumn(name="joning_id")
	private Joining joining;
	/**冲胚时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	private Date date;
	/**备注*/
	@Size(max=20)
	private String description;
	/**操作员*/
	@Size(max=20)
	private String operator;
	/**基因等级*/
	@Size(max=20)
	private String purposeType;
	/**总胚胎数*/
	private Integer total;
	/**未受精数*/
	private Integer infertile;
	/**退化胚胎数*/
	private Integer degenerate;
	/**可用胚胎数*/
	private Integer usable;
	/**移植鲜胚数*/
	private Integer number;
	/**冷冻胚胎数*/
	private Integer frozen;
	/**A级胚胎数*/
	private Integer alevel;
	/**B级胚胎数*/
	private Integer blevel;
	/**是否冲胚*/
	private String isEmbryoFlush;
	
	/**修改*/
	public EmbryoFlush update(EmbryoFlush embryoFlush) {
		this.total=embryoFlush.getTotal();
		this.infertile=embryoFlush.getInfertile();
		this.degenerate=embryoFlush.getDegenerate();
		this.usable=embryoFlush.getUsable();
		this.frozen=embryoFlush.getFrozen();
		this.alevel=embryoFlush.getAlevel();
		this.blevel=embryoFlush.getBlevel();
		this.purposeType=embryoFlush.getPurposeType();
		this.date=embryoFlush.getDate();
		this.operator=embryoFlush.getOperator();
		this.description=embryoFlush.getDescription();
		this.number=embryoFlush.getNumber();
		return this;
	}
	
	/**
	 * 冻胚校验修改
	 * */
	public Message updateVerify(Integer transNum,Integer frozenNumber){
		if (transNum==null){
			transNum=0;
		}
		if (frozenNumber==null){
			frozenNumber=0;
		}
		if (this.number<transNum){
			return GlobalConfig.setAbnormal("鲜胚移植数不能小于已移植数,该羊的已移植数为:"+transNum);
		}
		if (this.frozen<frozenNumber){
			return GlobalConfig.setAbnormal("冻胚移植数不能小于冻胚制作书,该羊已制作:"+frozenNumber+"枚胚胎");
		}
		return GlobalConfig.SUCCESS;
	}
	
	public EmbryoFlush setTotalReturnThis(Integer num){
		this.usable-=num;
		return this;
	}
	
}
