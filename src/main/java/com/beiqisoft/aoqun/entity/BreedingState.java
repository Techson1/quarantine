package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 繁殖状态
 * @author 程哲旭
 * @version 1.0
 * @time  2017年11月4日上午9:10:00
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_breedingState")
public class BreedingState extends BaseEntity{

	/**羊只表*/
	@ManyToOne @JoinColumn(name="base_info_id")
	public BaseInfo baseInfo;
	/**状态开始时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date date;
	/**当前繁殖状态*/
	public String state;
	
}
