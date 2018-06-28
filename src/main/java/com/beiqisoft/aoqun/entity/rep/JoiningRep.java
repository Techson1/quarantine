package com.beiqisoft.aoqun.entity.rep;

import com.beiqisoft.aoqun.util.MyUtils;

import lombok.Data;

@Data
public class JoiningRep {
	
	/**配种序号*/
	private String joiningSeq;
	/**配种数量*/
	private String joiningCount;
	/**配种测孕数*/
	private String resultCount;
	/**未测孕数*/
	private String notResult;
	/**怀孕数*/
	private String pregnancy;
	/**怀孕率*/
	private String percentage;
	
	public JoiningRep(){
		
	}
	
	public JoiningRep(Object[] o){
		this.joiningSeq=o[0].toString();
		this.joiningCount=o[1].toString();
		long o2= o[2]!=null?MyUtils.strToLong(o[2].toString()):0;
		Long o3= o[3]!=null?MyUtils.strToLong(o[3].toString()):0;
		this.resultCount=o2+o3+"";
		this.notResult=MyUtils.strToLong(this.joiningCount)-MyUtils.strToLong(this.resultCount)+"";
		this.pregnancy=o2+"";
		
		this.percentage=String.format("%.2f",(MyUtils.strToLong(this.pregnancy)*0.1)/(MyUtils.strToLong(joiningCount)*0.1)*100)+"%";
	}
	
	public void Add(JoiningRep rep){
		this.joiningSeq="合计";
		this.joiningCount=MyUtils.strToLong(this.joiningCount)+MyUtils.strToLong(rep.joiningCount)+"";
		this.resultCount=MyUtils.strToLong(this.resultCount)+MyUtils.strToLong(rep.resultCount)+"";
		this.notResult=MyUtils.strToLong(this.notResult)+MyUtils.strToLong(rep.notResult)+"";
		this.pregnancy=MyUtils.strToLong(this.pregnancy)+MyUtils.strToLong(rep.pregnancy)+"";
		this.percentage=String.format("%.2f",(MyUtils.strToLong(this.pregnancy)*0.1)/(MyUtils.strToLong(joiningCount)*0.1)*100)+"%";
	}
	
	public JoiningRep(String joiningSeq,String joiningCount){
		
	}
}
