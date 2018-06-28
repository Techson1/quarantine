package com.beiqisoft.aoqun.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.util.MyUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 耳标登记实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_codeRegister")
public class CodeRegister extends BaseEntity{
	
	/**品种id*/
	@ManyToOne @JoinColumn(name = "breed_id")
	private Breed breed;
	/**分厂id*/
	@ManyToOne @JoinColumn(name = "org_id")
	private Organization org;
	/**供应商*/
	@ManyToOne @JoinColumn(name = "customer_id")
	private Contact customer;
	/**入库时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date warehousingDate;
	@Transient
	private Date warehousingDateAssistStart;
	@Transient
	private Date warehousingDateAssistEnd;
	/**耳标类型*/
	@Size(max=20)
	private String type;
	/**性别*/
	@Size(max=2)
	private String sex;
	/**出生状态*/
	@Size(max=20)
	private String state;
	/**颜色*/
	@Size(max=20)
	private String color;
	/**记录人*/
	@Size(max=20)
	private String recorder;
	/**可视耳号*/
	@Size(max=20)
	private String code;
	/**电子耳号*/
	private Long visualCode;
	/**使用状态0:未用 1:已用*/
	@Size(max=20)
	private String useState;
	/**使用时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date useDate;
	//临时对象字段不存在数据库中
	/**开始号段*/
	@Transient
	private String startCode;
	/**结束号段*/
	@Transient
	private Integer endCode;
	/**开始可视号段*/
	@Transient
	private Long visualStartCode;
	/**结束可视号段*/
	@Transient
	private Long visualEndCode;
	/**数量*/
	@Transient
	private Integer number;
	/**前缀*/
	@Transient
	private String prefix;
	
	public CodeRegister(CodeRegister codeRegister,Integer number){
		this.warehousingDate=codeRegister.getWarehousingDate();
		this.type=codeRegister.getType();
		this.state=codeRegister.getState();
		this.color=codeRegister.getColor();
		this.recorder=codeRegister.getRecorder();
		this.prefix=codeRegister.getPrefix();
		this.useState=SystemM.PUBLIC_FALSE;
		this.useDate=codeRegister.getUseDate();
		if (codeRegister.getBreed()!=null && codeRegister.getBreed().getId()!=null){
			this.breed=codeRegister.getBreed();
		}
		this.org=codeRegister.getOrg();
		this.customer=codeRegister.getCustomer();
		this.visualCode=codeRegister.getVisualStartCode();
		if (SystemM.CODE_TYPE_ELECTRONIC_EAR_TAG.equals(this.type)){
			int start=Integer.parseInt(codeRegister.getStartCode());
			this.code=codeRegister.getPrefix()+MyUtils.complement(start+number, codeRegister.getStartCode().length());
			int codeSex = Integer.parseInt(code.substring(code.length()-1));
			if ((codeSex & 1)==0){
				this.sex=SystemM.PUBLIC_SEX_DAM;
			}else{
				this.sex=SystemM.PUBLIC_SEX_SIRE;
			}
		}
		else{
			this.visualCode=this.visualCode+number;
		}
		
	}
	
	/**
	 * 根据开始耳号及结束耳号返回结果集
	 * @return String[]
	 * */
	public String[] getCodes(){
		List<String> codeList=new ArrayList<String>();
		//codeList.add("");
		int start=Integer.parseInt(startCode);
		for (int i=start;i<start+number;i++){
			codeList.add(this.getPrefix()+MyUtils.complement(i, startCode.length()));
		}
		return MyUtils.listToStringArray(codeList);
	}
	
	public String[] getRfids(){
		List<String> rfidList = new ArrayList<String>();
		if (visualStartCode==null || visualEndCode==null){
			return MyUtils.listToStringArray(rfidList);
		}
		for(Long i=visualStartCode;i<=visualEndCode;i++){
			rfidList.add(i+"");
		}
		return MyUtils.listToStringArray(rfidList);
	}
	
	/**
	 * 修改使用状态并返回自身
	 * */
	public CodeRegister setUseStateReturnThis(String useState){
		this.useState=useState;
		if (SystemM.PUBLIC_TRUE.equals(useState)){
			this.useDate=new Date();
		}
		else{
			this.useDate=null;
		}
		return this;
	}
	
	
	/**
	 * 返回前缀+开始电子耳号
	 * */
	public String getPrefixStartCode(){
		return this.prefix+this.startCode;
	}
	
	/**
	 * 返回前缀+电子耳号
	 * */
	public String getPrefixCode(){
		return this.prefix+this.code;
	}
	/**
	 * 返回前缀+可视耳号
	 * */
	public String getPrefixVisualCode(){
		return this.prefix+this.visualCode;
	}
}
