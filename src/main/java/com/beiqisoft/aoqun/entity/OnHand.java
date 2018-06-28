package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.util.DateUtils;

/**
 * 饲舍存栏实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2018年1月28日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString
@Entity 
@Table(name = "onHand")
public class OnHand extends BaseEntity{

	/**圈舍*/
	@ManyToOne @JoinColumn(name="paddock_id")
	private Paddock paddock;
	/**数量*/
	private Long num;
	
	public OnHand(Paddock paddock,Long num){
		this.ctime = DateUtils.dateAddInteger(new Date(), -1);
		this.paddock = paddock;
		this.num = num;
	}
}
