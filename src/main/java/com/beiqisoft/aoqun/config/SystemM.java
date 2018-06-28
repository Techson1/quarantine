package com.beiqisoft.aoqun.config;

import org.springframework.util.ClassUtils;

/**
 * 系统设置基础类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
public class SystemM {
	
	/**项目基路径*/
	public static final String PATH=ClassUtils.getDefaultClassLoader().getResource("").getPath();
	/**文件导入导出路径*/
	public static final String FILE_PATH=PATH+"file/";
	
	/**空怀配种提示天数*/
	public static final String NONPREGNANT_CROSS_HINT_DAY="空怀配种提示天数";
	/**未孕配种提示天数*/
	public static final String UNPREGNANCY_CROSS_HINT_DAY="未孕配种提示天数";
	/**测孕提示天数*/
	public static final String PREGNANCY_HINT_DAY="测孕提示天数";
	/**产羔提示天数*/
	public static final String LAMBING_DAM_HINT_DAY="产羔提示天数";
	/**断奶提示天数*/
	public static final String WEANING_HINT_DAY="断奶提示天数";
	/**定级提示天数*/
	public static final String RANK_HINT_DAY="定级提示天数";
	/**称重提示天数*/
	public static final String WEIGHT_HINT_DAY="称重提示天数";
	/**久配不孕警告*/
	public static final String WARNING_NUMBER="久配不孕警告数";
	
	/**绩效考核-类型-饲养量*/
	public static final String PERFORMANCE_TYPE_FEEDING_COUNT ="1";
	/**绩效考核-类型-死亡*/
	public static final String PERFORMANCE_TYPE_DEATH="2";
	/**绩效考核-类型-疾病*/
	public static final String PERFORMANCE_TYPE_ILLNESS="3";
	/**绩效考核-类型-育种*/
	public static final String PERFORMANCE_TYPE_BREEDING="4";
	/**绩效考核-类型-增重*/
	public static final String PERFORMANCE_TYPE_DYNAMITING="5";
	
	/**称重-类型-出生重*/
	public static final String WEIGHT_TYPE_INITIAL="0";
	/**称重-类型-断奶重*/
	public static final String WEIGHT_TYPE_WEANING="1";
	/**称重-类型-普通重*/
	public static final String WEIGHT_TYPE_NORMAL="2";
	
	/**羊只-生理状态-羔羊*/
	public static final String BASE_INFO_PHYSIOLOGY_STATUS_LAMB="2";
	/**羊只-生理状态-青年羊*/
	public static final String BASE_INFO_PHYSIOLOGY_STATUS_YOUTH="3";
	/**羊只-生理状态-成年羊*/
	public static final String BASE_INFO_PHYSIOLOGY_STATUS_GROW="4";
	
	/**品种-纯种*/
	public static final String BREED_PUREBRED= "15";
	/**品种-杂交*/
	public static final String BREED_HYBRIDIZATION="16";
	/**品种-杂种*/
	public static final String BREED_CROSSBREED="17";
	
	/**用户-账号状态-web*/
	public static final String USER_ACCOUNT_STATUS_WEB="10";
	/**用户-账号状态-app*/
	public static final String USER_ACCOUNT_STATUS_APP="11";
	
	/**真*/
	public static final String PUBLIC_TRUE="1";
	/**假*/
	public static final String PUBLIC_FALSE="0";
	/**公*/
	public static final String PUBLIC_SEX_SIRE="1";
	/**母*/
	public static final String PUBLIC_SEX_DAM="2";
	
	/**正常*/
	public static final String NORMAL="1";
	/**疾病淘汰*/
	public static final String DEATH_WEED_OUT="2";
	/**育种淘汰*/
	public static final String DEATH_BREEDING ="3";
	/**死亡*/
	public static final String DEATH="4";
	/**疾病淘汰待审核*/
	public static final String STAY_DEATH_WEED_OUT="5";
	/**育种淘汰代审核*/
	public static final String STAY_DEATH_BREEDING="6";
	/**死亡代审核*/
	public static final String STAY_DEATH="7";
	/**审核失败*/
	public static final String STAY_DEFEATED="8";
	/**销售待审核*/
	public static final String STAY_MARKET="9";
	/**销售审核*/
	public static final String AUDIT_MARKET="10";
	/**调拨待审核*/
	public static final String DELIVERY="11";
	/**调拨审核*/
	public static final String ALLOT_DETAIL="12";
	/**旧死淘*/
	public static final String DEATH_FORMER_DATA_BASE="13";
	/**存档*/
	public static final Long FLAG=99L;
	
	/**定级-用途-销售*/
	public static final String GROUP_PURPOSE_MARKET="2";
	/**定级-用途-生产*/
	public static final String GROUP_PURPOSE_PRODUCTION="3";
	
	/**耳标-类型-可视耳标*/
	public static final String CODE_TYPE_ELECTRONIC_EAR_TAG="2";
	/**耳标-类型-电子耳标*/
	public static final String CODE_TYPE_VISUAL_EAR_TAG="3";
	
	/**耳标-出生状态-正常*/
	public static final String CODE_BIRTH_STATE_NORMAL="2";
	/**耳标-出生状态-死胎*/
	public static final String CODE_BIRTH_STATE_STILLBIRTH ="3";
	/**耳标-出生状态-畸形*/
	public static final String CODE_BIRTH_STATE_MALFORMATION="4";
	
	/**颜色—蓝色*/
	public static final String COLOR_BLUE="blue";
	/**颜色—黄色*/
	public static final String COLOR_YELLOW="yellow";
	/**颜色—白色*/
	public static final String COLOR_WHITE="white";
	/**颜色—粉色*/
	public static final String COLOR_PINK="pink";
	/**颜色—红色*/ 
	public static final String COLOR_RED="red";
	
	/**羊只-繁殖状态-无状态*/
	public static final String BASE_INFO_BREEDING_STATE_STATELESS="--";
	/**羊只-繁殖状态-空怀*/
	public static final String BASE_INFO_BREEDING_STATE_NONPREGNANT="10";
	/**羊只-繁殖状态-已配种*/
	public static final String BASE_INFO_BREEDING_STATE_CROSS="11";
	/**羊只-繁殖状态-未孕*/
	public static final String BASE_INFO_BREEDING_STATE_UNPREGNANCY="13";
	/**羊只-繁殖状态-妊娠*/
	public static final String BASE_INFO_BREEDING_STATE_GESTATION="14";
	/**羊只-繁殖状态-哺乳*/
	
	//胚胎移植状态
	public static final String BASE_INFO_BREEDING_STATE_LACTATION="15";
	/**羊只-繁殖状态-供体准备*/
	public static final String BASE_INFO_BREEDING_STATE_DONOR_PREPARE ="20";
	/**羊只-繁殖状态-受体准备*/
	public static final String BASE_INFO_BREEDING_STATE_RECEPTOR_PREPARE="21";
	/**羊只-繁殖状态-AI*/
	public static final String BASE_INFO_BREEDING_STATE_DONOR_AI="22";
	/**羊只-繁殖状态-供体恢复期*/
	public static final String BASE_INFO_BREEDING_STATE_DONOR_GET_WELL ="23";
	/**羊只-繁殖状态-已移植*/
	public static final String BASE_INFO_BREEDING_STATE_RECEPTOR_TRANSPLANT="24";
	//公羊
	/**羊只-繁殖状态-未调教*/
	public static final String BASE_INFO_BREEDING_NOT_SEMEN="25";
	/**羊只-繁殖状态-好*/
	public static final String BASE_INFO_BREEDING_GOOD="26";
	/**羊只-繁殖状态-中*/
	public static final String BASE_INFO_BREEDING_CENTRE="27";
	/**羊只-繁殖状态-差*/
	public static final String BASE_INFO_BREEDING_DIFFERENCE="28";
	/**羊只-繁殖状态-不爬羊*/
	public static final String BASE_INFO_BREEDING_SHEEP="29";
	/**羊只-繁殖状态-本交成功*/
	public static final String BASE_INFO_BREEDING_SUCCEED="30";
	
	/**配种方式-子宫角*/
	public static final String JOINING_TYPE_CORNUA_UTERI="2";
	/**配种方式-阴道输精*/
	public static final String JOINING_TYPE_VAGINA_SPERM="3";
	/**配种方式-本交*/
	public static final String JOINING_TYPE_THIS_PAY="4";
	
	/**发情状态-未知*/
	public static final String HEAT_STATE_NATURAL_ESTRUS="2";
	/**发情状态-自然发情*/
	public static final String NATURE_HEAT="3";
	/**发情状态-主动辅助-发情*/
	public static final String INITIATIVE_HEAT="4";
	/**发情状态-主动辅助-未发*/
	public static final String INITIATIVE_NOT_HEAT="5";
	/**发情状态-被动辅助-发情*/
	public static final String PASSIVITY_HEAT="6";
	/**发情状态-被动辅助-未发*/
	public static final String PASSIVITY_NOT_HEAT="7";
	
	/**测孕结果-怀孕*/
	public static final String RESULTS_PREGNANCY="2";
	/**测孕结果-未孕*/
	public static final String RESULTS_UNPREGNANCY="3";
	
	/**胎次类型-自然繁殖*/
	public static final String PARITY_TYPE_NB="NB";
	/**胎次类型-供体*/
	public static final String PARITY_TYPE_DO="DO";
	/**胎次类型-鲜胚受体*/
	public static final String PARITY_TYPE_FR="FR";
	/**胎次类型-冻胚受体*/
	public static final String PARITY_TYPE_FO="FO";
	
	/**母羊产羔-生产难易-顺产*/
	public static final String LAMBINGDAM_EASYFLAG_EUTOCIA="1";
	/**母羊产羔-生产难易-助产*/
	public static final String LAMBINGDAM_EASYFLAG_ACCOUCHE="2";
	/**母羊产羔-生产难易-剖腹产*/
	public static final String LAMBINGDAM_EASYFLAG_CESAREAN="3";
	
	/**定级-级别-未定级*/
	public static final String RANKTEST_RANK_NOT_RANK="0";
	/**定级-级别-核心*/
	public static final String RANKTEST_RANK_KERNEL="2";
	/**定级-级别-后备核心群*/
	public static final String RANKTEST_RANK_MARKET_RESERVE_KERNEL="3";
	/**定级-级别-生产*/
	public static final String RANKTEST_RANK_PRODUCTION="4";
	/**定级-级别-销售*/
	public static final String RANKTEST_RANK_MARKET="5";
	/**定级-级别-淘汰群 @deprecated */
	public static final String RANKTEST_RANK_MARKET_RESERVE_WEED="6";
	/**受体群 @deprecated*/
	public static final String RANKTEST_RANK_receptor="7";
	
	/**繁殖-参数-羔羊*/
	public static final String BREED_PARAMETER_LAMB="羔羊";
	/**繁殖-参数-青年羊*/
	public static final String BREED_PARAMETER_YOUTH="青年羊";
	/**繁殖-参数-成年羊*/
	public static final String BREED_PARAMETER_GROW="成年羊";
	/**繁殖-参数-供体回复期*/
	public static final String BREED_PARAMETER_DONOR_REPLY="供体回复期";
	/**母羊性成熟日龄/初配日龄*/
	public static final String BREED_PARAMETER_CROSS="母羊性成熟日龄";
	/**怀孕前期/后期*/
	public static final String BREED_PARAMETER_GESTATION="怀孕前期/后期";
	/**母羊一测日期*/
	public static final String BREED_PREGNANCY_ONE="一测日期";
	/**母羊二测日期*/
	public static final String BREED_PREGNANCY_TWO="二测日期";
	/**怀孕天数*/
	public static final String BREED_PRODUCTION="怀孕天数";
	/**断奶日期*/
	public static final String BREED_WEANING="断奶日龄";
	/**公羊性成熟日龄/采精日龄*/
	public static final String SEMEN_DAY_AGE="公羊性成熟日龄";
	
	/**基因等级-核心群*/
	public static final String PURPOSE_TYPE_CORE="2";
	/**基因等级-生产群*/
	public static final String PURPOSE_TYPE_PRODUCTION="3";
	
	/**处理措施-销售*/
	public static final String TREATMENT_MARKET="1";
	/**处理措施-屠宰*/
	public static final String TREATMENT_SLAUGHTER="2";
	/**处理措施-无害化处理*/
	public static final String TREATMENT_INNOCENT="3";
	
	/**原料-混合*/
	public static final String MATERIAL_BLEND="1";
	/**原料-纯料*/
	public static final String MATERIAL_BLOOD="2";
	
	//根据羊只繁殖状态返回中文含义
	public static String baseInfoBreedingRturnChinese(String baseInfoBreeding){
		if (BASE_INFO_BREEDING_STATE_NONPREGNANT.equals(baseInfoBreeding)){
			return "空怀";
		}
		if (BASE_INFO_BREEDING_STATE_CROSS.equals(baseInfoBreeding)){
			return "配种";
		}
		if (BASE_INFO_BREEDING_STATE_UNPREGNANCY.equals(baseInfoBreeding)){
			return "未孕";
		}
		if (BASE_INFO_BREEDING_STATE_CROSS.equals(baseInfoBreeding)){
			return "配种";
		}
		if (BASE_INFO_BREEDING_STATE_GESTATION.equals(baseInfoBreeding)){
			return "妊娠";
		}
		if (BASE_INFO_BREEDING_STATE_LACTATION.equals(baseInfoBreeding)){
			return "哺乳";
		}
		if (BASE_INFO_BREEDING_STATE_DONOR_PREPARE.equals(baseInfoBreeding)){
			return "供体准备";
		}
		if (BASE_INFO_BREEDING_STATE_RECEPTOR_PREPARE.equals(baseInfoBreeding)){
			return "受体准备";
		}
		if (BASE_INFO_BREEDING_STATE_DONOR_AI.equals(baseInfoBreeding)){
			return "AI";
		}
		if (BASE_INFO_BREEDING_STATE_DONOR_GET_WELL.equals(baseInfoBreeding)){
			return "供体恢复期";
		}
		if (BASE_INFO_BREEDING_STATE_RECEPTOR_TRANSPLANT.equals(baseInfoBreeding)){
			return "已移植";
		}
		return "--";
	}

	public static String type(String type) {
		if (DEATH_WEED_OUT.equals(type)){
			return STAY_DEATH_WEED_OUT;
		}
		if (DEATH_BREEDING.equals(type)){
			return STAY_DEATH_BREEDING;
		}
		if (DEATH.equals(type)){
			return STAY_DEATH;
		}
		return type;
	}
}
