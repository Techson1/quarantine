package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 鲜胚移植实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_embryoTransplant")
public class EmbryoTransplant extends BaseEntity{
	/**冲胚*/
	@ManyToOne @JoinColumn(name="embryoFlush_id")
	public EmbryoFlush embryoFlush;
	/**供体羊*/ 
	@ManyToOne @JoinColumn(name="donor_id")
	public BaseInfo donor;
	/**公羊*/
	@ManyToOne @JoinColumn(name="sire_id")
	public BaseInfo sire;
	/**受体羊*/
	@ManyToOne @JoinColumn(name="receptor_id")
	public BaseInfo receptor;
	/**项目*/
	@ManyToOne @JoinColumn(name="project_id")
	public EmbryoProject project;
	/**受体胎次*/
	@ManyToOne @JoinColumn(name="receptor_parity_id")
	public Parity receptorParity;
	
	/**移植记录单号*/
	@Size(max=20)
	public String sheetCode;
	/**移植数*/
	public Integer transNum;
	/**移植日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date date;
	/**质量等级*/
	 @Size(max=25)
	private String qualityGrade;
	 
	public EmbryoTransplant(String sheetCode,Integer transNum,Date date
			,String qualityGrade,EmbryoFlush embryoFlush,String recorder){
		this.sheetCode=sheetCode;
		this.transNum=transNum;
		this.date=date;
		this.qualityGrade=qualityGrade;
		this.embryoFlush=embryoFlush;
		this.project=embryoFlush.getProject();
		this.recorder=recorder;
	}
}
