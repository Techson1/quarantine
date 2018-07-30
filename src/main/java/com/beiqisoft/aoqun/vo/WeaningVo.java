package com.beiqisoft.aoqun.vo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WeaningVo {

	private Long id; 
	//耳号
	private String code;
	//品种
	private String breedName;
	//胎次
	private String parityMaxNumber;
	//产羔日期
	private String bornDate;
	//产羔总数
	private String bornTimes;
	//断奶日期
	private String weaningDate;
	//操作人
	private String recorder;
	//更新时间
	private String ctime;
	
	
}
