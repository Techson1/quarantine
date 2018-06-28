package com.beiqisoft.aoqun.entity.domain;

import java.util.Date;

import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.MyUtils;

import lombok.Data;

@Data
public class FiltrateCondition {
	//羊只
	/**自己*/
	private String code;
	/**种爸*/
	private String sire;
	/**种爸强大后宫中该羊的母亲*/
	private String dam;
	/**爸爸的妈妈叫什么*/
	private String sireDam;
	/**爸爸的爸爸叫什么*/
	private String sireSire;
	/**妈妈的妈妈叫什么*/
	private String damDam;
	/**妈妈的爸爸叫什么*/
	private String damSire;
	/**性别*/
	private String sex;
	/**开始出生日期*/
	private Date birthDayAssistStart;
	/**结束出生日期*/
	private Date birthDayAssistEnd;
	/**同胎数*/
	private String theSameFetus;
	/**最小出生重*/
	private String minInitialWeigh;
	/**最大出生重*/
	private String maxInitialWeigh;
	/**最小断奶日龄*/
	private Integer minWeaningDay;
	/**最大断奶日龄*/
	private Integer maxWeaningDay;
	/**最小断奶重*/
	private String minWeaningWeight;
	/**最大断奶重*/
	private String maxWeaningWeight;
	/**出生状态*/
	private String state;
	/**生长阶段*/
	private String bornStatus;
	/**繁殖状态*/
	private String breedingState;
	/**繁殖状态明细*/
	private String breedingStateDetail;
	/**库存状态*/
	private Long physiologyStatus;
	/**分厂id*/
	private Long orgId;
	
	public String baseInfoForm(String className) {//
		StringBuffer stringBuffer= new StringBuffer("");
		if (this.sire!=null){
			stringBuffer.append(" INNER JOIN "+className+".sire sire ");
		}
		if (this.dam!=null){
			stringBuffer.append(" INNER JOIN "+className+".dam dam ");
		}
		if (this.sireDam!=null){
			stringBuffer.append(" INNER JOIN "+className+".sire.dam sireDam ");
		}
		if (this.sireSire!=null){
			stringBuffer.append(" INNER JOIN "+className+".sire.sire sireSire ");
		}
		if (this.damDam!=null){
			stringBuffer.append(" INNER JOIN "+className+".dam.dam damDam ");
		}
		if (this.damSire!=null){
			stringBuffer.append(" INNER JOIN "+className+".dam.sire damSire ");
		}
		return stringBuffer.toString();
	}
	
	public String baseInfoQuery(String className){
		StringBuffer stringBuffer= new StringBuffer("");
		if (this.code!=null){
			stringBuffer.append(" AND "+className+".code = '"+this.code+"' ");
		}
		if (this.sire!=null){
			stringBuffer.append(" AND "+className+".sire.code = '"+this.sire+"' ");
		}
		if (this.dam!=null){
			stringBuffer.append(" AND "+className+".dam.code = '"+this.dam+"' ");
		}
		if (this.sireDam!=null){
			stringBuffer.append(" AND "+className+".sire.dam.code = '"+this.sireDam+"' ");
		}
		if (this.sireSire!=null){
			stringBuffer.append(" AND "+className+".sire.sire.code = '"+this.sireSire+"' ");
		}
		if (this.damDam!=null){
			stringBuffer.append(" AND "+className+".dam.dam.code = '"+this.damDam+"' ");
		}
		if (this.damSire!=null){
			stringBuffer.append(" AND "+className+".dam.sire.code = '"+this.damSire+"' ");
		}
		if (this.sex!=null){
			stringBuffer.append(" AND "+className+".sex = '"+this.sex+"' ");
		}
		if (this.birthDayAssistStart!=null){
			stringBuffer.append(" AND "+className+".birthDay >= "+DateUtils.DateToStr(this.birthDayAssistStart)+" ");
		}
		if (this.birthDayAssistEnd!=null){
			stringBuffer.append(" AND "+className+".birthDay <= "+DateUtils.DateToStr(this.birthDayAssistEnd)+" ");
		}
		if (this.theSameFetus!=null){
			stringBuffer.append(" AND "+className+".theSameFetus = '"+this.theSameFetus+"' ");
		}
		if (this.minInitialWeigh!=null){
			stringBuffer.append(" AND CAST("+className+".initialWeigh AS double ) >= "+MyUtils.strToDoble(this.minInitialWeigh)+" ");
		}
		if (this.maxInitialWeigh!=null){
			stringBuffer.append(" AND CAST("+className+".initialWeigh AS double ) <= "+MyUtils.strToDoble(this.maxInitialWeigh)+" ");
		}
		if (this.minWeaningWeight!=null){
			stringBuffer.append(" AND CAST("+className+".weaningWeight AS double ) >= "+MyUtils.strToDoble(this.minWeaningWeight)+" ");
		}
		if (this.maxWeaningWeight!=null){
			stringBuffer.append(" AND CAST("+className+".weaningWeight AS double ) <= "+MyUtils.strToDoble(this.maxWeaningWeight)+" ");
		}
		if (this.state!=null){
			stringBuffer.append(" AND "+className+".state = '"+this.state+"' ");
		}
		if (this.bornStatus!=null){
			stringBuffer.append(" AND "+className+".bornStatus = '"+this.bornStatus+"' ");
		}
		if(this.breedingState!=null){
			stringBuffer.append(" AND "+className+".breedingState = '"+this.breedingState+"' ");
		}
		if (this.breedingStateDetail!=null){
			stringBuffer.append(" AND "+className+".breedingState = '"+this.breedingStateDetail+"' ");
		}
		if (this.physiologyStatus!=null){
			stringBuffer.append(" AND "+className+".physiologyStatus = "+this.physiologyStatus+" ");
		}
		if (this.orgId!=null){
			stringBuffer.append(" AND "+className+".org.id = "+this.orgId+" ");
		}
		return stringBuffer.toString();
	}
	
	//毛色
	/**色斑打分*/
	private String splash;
	/**角*/
	private String horn;
	/**毛发打分*/
	private String hair;
	/**牙齿咬合*/
	private String tooth;
	/**蹄颜色打分*/
	private String footColor;
	/**嘴颜色打分*/
	private String mouthColor;
	/**脂肪分表*/
	private String fats;
	/**开始日期*/
	private Date dateAssistStart;
	/**结束日期*/
	private Date dateAssistEnd;
	
	public String looksQuery(String className){
		StringBuffer stringBuffer= new StringBuffer("");
		if (this.splash!=null){
			stringBuffer.append(" AND "+className+".splash = '"+this.splash+"' ");
		}
		if (this.horn!=null){
			stringBuffer.append(" AND "+className+".horn = '"+this.horn+"' ");
		}
		if (this.hair!=null){
			stringBuffer.append(" AND "+className+".hair = '"+this.hair+"' ");
		}
		if (this.tooth!=null){
			stringBuffer.append(" AND "+className+".tooth = '"+this.tooth+"' ");
		}
		if (this.footColor!=null){
			stringBuffer.append(" AND "+className+".footColor = '"+this.tooth+"' ");
		}
		if (this.mouthColor!=null){
			stringBuffer.append(" AND "+className+".mouthColor = '"+this.mouthColor+"' ");
		}
		if (this.fats!=null){
			stringBuffer.append(" AND "+className+".fats = '"+this.fats+"' ");
		}
		if (this.dateAssistStart!=null){
			stringBuffer.append(" AND "+className+".date >= "+DateUtils.DateToStr(this.dateAssistStart)+" ");
		}
		if (this.dateAssistEnd!=null){
			stringBuffer.append(" AND "+className+".date <= "+DateUtils.DateToStr(this.dateAssistEnd)+" ");
		}
		System.err.println("结果:"+stringBuffer.toString());
		return stringBuffer.toString();
	}
}


class SalesCondition{
	
}

class joiningCondition{
	
}

class PregnancyCondition{
	
}

class LamdbaCondition{
	
}

class WeaningCondition{
	
}

