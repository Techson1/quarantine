package com.beiqisoft.aoqun.entity.rep;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

import com.beiqisoft.aoqun.entity.Breed;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 销售报表
 * 数据库接受实体类
 * */
@Data
public class SalesRep {
	private Breed breed;
	private String breedName;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date repDate;
	private Long count;
	private String price;
	
	public SalesRep(Breed breed,Date repDate,Long count, String price){
		this.breed=breed;
		this.breedName=breed.getId()+"";
		this.repDate=repDate;
		this.count=count;
		this.price=price;
	}
	
	public SalesRep(Breed breed){
		this.breed=breed;
	}

	@Override
	public String toString() {
		return "SalesRep [breedName=" + breedName + ", repDate=" + repDate
				+ ", count=" + count + ", price=" + price + ",breedId="+breed.getId()+"]";
	}
	
	
}

/**
 * 价格明细实体类
 * */
@Data
class SalesRepDatail{
	private Date repdate;
	private Long count;
	private Long price;
}

/**
 * 前端封装实体类
 * */
@Data
class SalesList{
	private Breed breed;
	private List<SalesRepDatail> reps;
	
	
}