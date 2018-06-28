package com.beiqisoft.aoqun.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
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

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.util.MyUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 品种实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_breed")
@DynamicUpdate(true)
public class Breed extends BaseEntity{

	/**是否列表显示*/
	@Size(max=20)
	private String flag;
	/**品种名称*/
	@Size(max=20)
	private String breedName;
	/**品种类型*/
	@Size(max=20)
	private String breedType;
	/**对应纯种品种用id隔开*/
	@Size(max=255)
	private String breedIds;
	/**记录人*/
	@Size(max=64)
	private String recorder;
	/**更新时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate=new Date();
	
	@Transient
	private Long breedId;
	/**杂交对应纯种的集合*/
	@Transient
	private List<Breed> breeds;
	/**血统*/
	@Transient
	private String blood;
	
	
	public Breed(Long id){
		this.id=id;
	}
	
	public List<Breed> getBreedsAdd(){
		if (this.breeds==null){
			this.breeds=new ArrayList<Breed>();
		}
		return this.breeds;
	}
	
	/**
	 * 获取血统,如果血统为空则返回id
	 * */
	public String getBloodOrId(){
		if (this.breedIds==null || "".equals(breedIds)){
			return ""+this.id;
		}
		return breedIds;
	}
	
	/**
	 * 计算血统
	 * @param id
	 * 			血统
	 * @return 血统
	 * */
	public String getBloodIncludeBlood(String id){
		return Arrays.asList((this.getBloodOrId()+","+id).split(",")).stream().distinct().sorted((a,b)
				->MyUtils.strToLong(a)
				.compareTo(MyUtils.strToLong(b))).collect(Collectors.joining(","));
	}

	public Breed setBreedIdsAndSoft() {
		if ("".equals(this.breedIds)){
			this.breedIds=null;
		}
		else{
			this.breedIds=Arrays.asList(breedIds.split(",")).stream().sorted((a,b) -> MyUtils.strToLong(a)
					.compareTo(MyUtils.strToLong(b))).collect(Collectors.joining(","));;
		}
		return this;
	}
}
