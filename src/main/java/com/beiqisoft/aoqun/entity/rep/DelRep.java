package com.beiqisoft.aoqun.entity.rep;

import com.beiqisoft.aoqun.entity.DeathDisposalReason;

import lombok.Data;

/**
 * 死亡淘汰报表
 * 数据库接受实体类
 * */
@Data
public class DelRep {

	private String reason;
	private Long count;
	private String proportion;
	
	public DelRep(){
		
	}
	
	public DelRep(String reason,Long count,String proportion){
		this.reason=reason;
		this.count=count;
		this.proportion=proportion;
	}
	
	public DelRep(DeathDisposalReason death,Long count){
		if(death!=null){
			reason=death.getName();
		}
		this.count=count;
	}
	
}
