package com.beiqisoft.aoqun.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 绩效考核
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_performance")
public class Performance extends BaseEntity{
	/**员工*/
	@ManyToOne @JoinColumn(name="contact_id")
	private Contact contact;
	/**
	 * 类型<BR>
	 * 饲养量:1<BR> 死亡:2<BR> 疾病:3<BR>育种:4<BR>增重:5<BR>
	 * */
	@Size(max=20)
	private String type;
	/**绩效日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	/**数量*/
	@Size(max=20)
	private String sum;
	/**年*/
	private Integer year;
	/**月*/
	private Integer month;
	@Size(max=10)
	private String day1;
	@Size(max=10)
	private String day2;
	@Size(max=10)
	private String day3;
	@Size(max=10)
	private String day4;
	@Size(max=10)
	private String day5;
	@Size(max=10)
	private String day6;
	@Size(max=10)
	private String day7;
	@Size(max=10)
	private String day8;
	@Size(max=10)
	private String day9;
	@Size(max=10)
	private String day10;
	@Size(max=10)
	private String day11;
	@Size(max=10)
	private String day12;
	@Size(max=10)
	private String day13;
	@Size(max=10)
	private String day14;
	@Size(max=10)
	private String day15;
	@Size(max=10)
	private String day16;
	@Size(max=10)
	private String day17;
	@Size(max=10)
	private String day18;
	@Size(max=10)
	private String day19;
	@Size(max=10)
	private String day20;
	@Size(max=10)
	private String day21;
	@Size(max=10)
	private String day22;
	@Size(max=10)
	private String day23;
	@Size(max=10)
	private String day24;
	@Size(max=10)
	private String day25;
	@Size(max=10)
	private String day26;
	@Size(max=10)
	private String day27;
	@Size(max=10)
	private String day28;
	@Size(max=10)
	private String day29;
	@Size(max=10)
	private String day30;
	@Size(max=10)
	private String day31;
	
	public Performance(Contact contact,String type,Date date,Integer year,Integer month){
		this.contact = contact;
		this.type = type;
		this.date = DateUtils.dateAddInteger(new Date(), -1);
		this.year=year;
		this.month=month;
	}
	
	/**
	 * 初始化日期都初始化为0
	 * */
	public Performance init(){
		this.day1="0";
		this.day2="0";
		this.day3="0";
		this.day4="0";
		this.day5="0";
		this.day6="0";
		this.day7="0";
		this.day8="0";
		this.day9="0";
		this.day10="0";
		this.day11="0";
		this.day12="0";
		this.day13="0";
		this.day14="0";
		this.day15="0";
		this.day16="0";
		this.day17="0";
		this.day18="0";
		this.day19="0";
		this.day20="0";
		this.day21="0";
		this.day22="0";
		this.day23="0";
		this.day24="0";
		this.day25="0";
		this.day26="0";
		this.day27="0";
		this.day28="0";
		this.day29="0";
		this.day30="0";
		this.day31="0";
		return this;
	}
	
	/**
	 * 根据日期赋值数量
	 * */
	public Performance dayWrite(String num){
		String day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"";
		switch(day){
			case "1":
				this.day1=num;
				break;
			case "2":
				this.day2=num;
				break;
			case "3":
				this.day3=num;
				break;
			case "4":
				this.day4=num;
				break;
			case "5":
				this.day5=num;
				break;
			case "6":
				this.day6=num;
				break;
			case "7":
				this.day7=num;
				break;
			case "8":
				this.day8=num;
				break;
			case "9":
				this.day9=num;
				break;
			case "10":
				this.day10=num;
				break;
			case "11":
				this.day11=num;
				break;
			case "12":
				this.day12=num;
				break;
			case "13":
				this.day13=num;
				break;
			case "14":
				this.day14=num;
				break;
			case "15":
				this.day15=num;
				break;
			case "16":
				this.day16=num;
				break;
			case "17":
				this.day17=num;
				break;
			case "18":
				this.day18=num;
				break;
			case "19":
				this.day19=num;
				break;
			case "20":
				this.day20=num;
				break;
			case "21":
				this.day21=num;
				break;
			case "22":
				this.day22=num;
				break;
			case "23":
				this.day23=num;
				break;
			case "24":
				this.day24=num;
				break;
			case "25":
				this.day25=num;
				break;
			case "26":
				this.day26=num;
				break;
			case "27":
				this.day27=num;
				break;
			case "28":
				this.day28=num;
				break;
			case "29":
				this.day29=num;
				break;
			case "30":
				this.day30=num;
				break;
			 default:
				this.day31=num;
		}
		return this;
	} 
	
}
