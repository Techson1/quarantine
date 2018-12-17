package com.beiqisoft.aoqun.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.Pvg;
import com.beiqisoft.aoqun.repository.PvgRepository;
import com.beiqisoft.aoqun.service.UserService;
import com.beiqisoft.aoqun.util.json.JSON;
//import org.wangdong.springboot.repository.view.UserViewRepository;
import com.beiqisoft.aoqun.base.BaseController;

/**
 * 权限访问控制类
 * @author 王栋
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "pvg")
public class PvgController extends BaseController<Pvg, UserService> {

	@Autowired
	PvgRepository pvgRepository;
	
	@JSON(type=Map.class,include="appPvgs")
	@JSON(type=Pvg.class,include="name,isPvg")
	@RequestMapping(value = "appPvgs")
	public Object appPvgs(){
		Map<String,Object> pvgMap=new HashMap<>();
		if (debugFlag || currentUser().getUserName().equals("admin")){
		   List<Pvg> pvg=pvgRepository.findByType(Pvg.TYPE_APP)
					.stream().map(x -> x.setIsPvgReturnThis(SystemM.PUBLIC_TRUE)).collect(Collectors.toList());
		   pvgMap.put("appPvgs",pvg);
		}
		else{
			List<Pvg> pvg = pvgRepository.findByType(Pvg.TYPE_APP);
			List<Pvg> userPvg = currentUser().getRole().getPvgs();
			for (Pvg p:pvg){
				for (Pvg u:userPvg){
					if (p.equals(u)){
						p.setIsPvg(SystemM.PUBLIC_TRUE);
					}
				}
			}
			pvgMap.put("appPvgs",pvg);
		}
		return pvgMap;
	}
	
	@RequestMapping(value = "appWebPvgs")
	public Object appWebPvgs(){
		return pvgRepository.findByType(Pvg.TYPE_APP);
	}
	
	@RequestMapping(value = "appPvgsMap")
	public Map<String,String> appPvgsMap(){
		Map<String,String> pvgMap=new HashMap<>();
		if (debugFlag || currentUser().getUserName().equals("admin")){
			pvgRepository.findByType(Pvg.TYPE_APP).forEach(x -> pvgMap.put(x.getName(), SystemM.PUBLIC_TRUE));
		}else{
			
		}
		return pvgMap;
	}
	
	@RequestMapping(value = "list")
	public Object list(){
		if(debugFlag || currentUser().getUserName().equals("admin")){
			String[] pvgs= new String[]{"繁殖参数","集团信息","分场管理","羊只品种设置","羊只销售定价","胚胎销销售定价"
					,"疾病及淘汰原因","育种淘汰原因","死亡原因","公羊缺陷","母羊缺陷","账号管理","角色管理","供应商维护"
					,"系统维护","免疫保健项目"};
			result.put("allPvgs", pvgRepository.findByNameIn(pvgs));
			result.put("selfPvgs",getIds(pvgRepository.findAll()));
		}else{
			result.put("allPvgs", pvgRepository.findByType(Pvg.TYPE_PARENT));
			result.put("selfPvgs", currentUser().getRole().getPvgIds());
		}
		return result;
	}
	
	/**
	 * 权限列表
	 * 		查询全部权限,只传输id,和name
	 * */
	@JSON(type=Pvg.class,include="id,name")
	@RequestMapping(value = "pvgIdAndName")
	public Object pvgIdAndName(){
		if(debugFlag){
			result.put("allPvgs", pvgRepository.findByParentIsNull());
			result.put("selfPvgs",getIds(pvgRepository.findAll()));
		}else{
			result.put("allPvgs", pvgRepository.findByParentIsNull());
			//result.put("selfPvgs", userService.getRepository().findOne(currentUser().getId()).getRole().getPvgIds());
		}
		return result;
	}
	
	@RequestMapping(value = "init")
	public Message init(){
		pvgRepository.deleteAll();
		Pvg p0 = new Pvg("index", "首页", "fa-tachometer");
		Pvg p1 = new Pvg("#", "消息通知", "fa-bell-o");	
		Pvg p19 = new Pvg("message/semenHint","调教提示","",p1);
		Pvg p10 = new Pvg("message/crosslist", "配种提示警告", "",p1);
		Pvg p11 = new Pvg("message/pregInspectlist", "妊检提示警告", "",p1);
		Pvg p12 = new Pvg("message/productionlist", "产羔提示警告", "",p1);
		Pvg p13 = new Pvg("message/abortionlist", "流产提示警告", "",p1);
		Pvg p14 = new Pvg("message/weaninglist", "断奶提示警告", "",p1);
		Pvg p1a = new Pvg("message/notjininglist", "未孕在配提示", "",p1);
		Pvg p17 = new Pvg("message/infertilitylist", "久配不孕警告", "",p1);
		Pvg p15 = new Pvg("message/weightlist", "称重提示警告", "",p1);
		Pvg p16 = new Pvg("message/gradinglist", "定级提示警告", "",p1);
		Pvg p1b = new Pvg("message/diagnosis", "诊疗提示", "",p1);
		Pvg p18 = new Pvg("message/set", "参数设置", "",p1);
		//Pvg p2 = new Pvg("#", "大数据", "fa-desktop");
		Pvg p3 = new Pvg("#", "统计报表", "fa-area-chart");
		Pvg p30 = new Pvg("count/sheepManage", "羊只管理存栏表", "",p3);
		Pvg p31 = new Pvg("count/breed", "繁殖报表", "",p3);
		Pvg p32 = new Pvg("count/sale", "销售报表", "",p3);
		//Pvg p33 = new Pvg("count/sale1", "销售报表2", "",p3);
		Pvg p34 = new Pvg("count/feeding", "饲喂明细表", "",p3);
		Pvg p35 = new Pvg("count/ewe", "母羊繁殖统计表", "",p3);
		//Pvg p36 = new Pvg("count/ram", "公羊存栏统计表", "",p3);
		Pvg p37 = new Pvg("count/noProduct", "非生产天数", "",p3);
		Pvg p38 = new Pvg("count/parity", "胎次间隔", "",p3);
		Pvg p39 = new Pvg("earTage/rfidChange","电子标更换记录","",p3);
		Pvg p310 = new Pvg("count/monthcount","羊只存栏月报表","",p3);
		Pvg p311 = new Pvg("count/damBreedStateRep","母羊繁殖日报表","",p3);
		Pvg p312 = new Pvg("house/deathlist","诊疗死淘报表","",p3);
		Pvg p313 = new Pvg("count/popAnalysis","种群分析","",p3);
		//Pvg p39 = new Pvg("count/Classification", "分级存栏报告", "",p3);
		Pvg p401 = new Pvg("house/ranklist","月龄定级存栏","",p3);
		Pvg p402 = new Pvg(" house/breedlist","繁殖状态存栏表","",p3);
		Pvg p3a = new Pvg("count/dataView", "数据视图", "",p3);
		Pvg p4 = new Pvg("#", "年度规划", "fa-file-zip-o");
		Pvg p40 = new Pvg("productionIndex/list", "生产指标", "",p4);
		Pvg p41 = new Pvg("breedBudget/list", "繁殖预算", "",p4);
		Pvg p42 = new Pvg("deathBudget/list", "死淘预算", "",p4);
		Pvg p43 = new Pvg("saleBudget/list", "销售预算", "",p4);
		Pvg p5 = new Pvg("#", "育种中心", "fa-calendar");
		Pvg p50 = new Pvg("matching/list", "选配方案", "",p5);
		Pvg p51 = new Pvg("#", "体重数据", "",p5);
		Pvg p510 = new Pvg("weight/originallist", "原始数据", "",p51);
		Pvg p511 = new Pvg("weight/correctinglist", "校正数据", "",p51);
		Pvg p52 = new Pvg("sizeDetermination/list", "体尺测定", "",p5);
		Pvg p53 = new Pvg("phaseDetermination/list", "品相测定", "",p5);
		Pvg p69 = new Pvg("sheepGrading/list", "羊只定级",  "",p5);
		Pvg p54 = new Pvg("#", "基因管理", "",p5);
		Pvg p55 = new Pvg("breederOut/list", "育种淘汰", "",p5);
		Pvg p6 = new Pvg("#", "繁殖管理", "fa-venus-mars");
		Pvg p60 = new Pvg("animal/list", "种羊档案",  "",p6);
		Pvg p61 = new Pvg("semen/list", "采精调教",  "",p6);
		Pvg p621 = new Pvg("cross/list", "配种登记",  "",p6);
		Pvg p63 = new Pvg("#", "胚胎移植",  "",p6);
		Pvg p630 = new Pvg("embryo/list", "鲜胚移植及冻胚制作","",p63);
		Pvg p631 = new Pvg("frozenEmbryo/list", "冻胚移植",  "",p63);
		Pvg p64 = new Pvg("pregInspect/list", "测孕登记",  "",p6);
		Pvg p65 = new Pvg("abortion/list", "流产登记",  "",p6);
		Pvg p66 = new Pvg("production/list", "产羔登记",  "",p6);
		Pvg p67 = new Pvg("weaning/list", "断奶登记",  "",p6);
		Pvg p68 = new Pvg("defect/list", "缺陷登记",  "",p6);
		Pvg p71 = new Pvg("turnBar/list", "转栏管理", "",p6);
		//Pvg p7 = new Pvg("#", "羊舍管理", "fa-sort-numeric-asc");
		//Pvg p72 = new Pvg("environment/list", "环境监控", "",p7);
		Pvg p8 = new Pvg("#", "诊疗中心", "fa-medkit");
		Pvg p80 = new Pvg("immunePlan/list", "免疫计划", "",p8);
		Pvg p81 = new Pvg("immuneHealth/list", "免疫保健", "",p8);
		Pvg p82 = new Pvg("diseaseTreatment/list", "疾病诊疗", "",p8);
		Pvg p83 = new Pvg("death/list", "死亡登记", "",p8);
		Pvg p84 = new Pvg("disinfection/list", "消毒记录", "",p8);
		Pvg p85 = new Pvg("diseaseOut/list", "疾病淘汰", "",p8);
		Pvg p9 = new Pvg("#", "营养饲喂", "fa-sliders");
		Pvg p90 = new Pvg("rawMaterial/list", "饲料原料", "",p9);
		Pvg p91 = new Pvg("concentrate/list", "精料配方", "",p9);
		Pvg p92 = new Pvg("rationFormula/list", "日粮配方", "",p9);
		Pvg p93 = new Pvg("group/list", "饲喂群组", "",p9);
		Pvg p94 = new Pvg("feedingRecord/list", "饲喂记录", "",p9);
		Pvg pa = new Pvg("#", "调拨管理", "fa-tag");
		Pvg pa0 = new Pvg("#", "羊只调拨", "",pa);
		Pvg pa00 = new Pvg("turnOut/list", "转出管理", "",pa0);
		Pvg pa01 = new Pvg("turnInto/list", "转入管理", "",pa0);
		Pvg pb  = new Pvg("#", "外购登记","fa-dropbox");
		Pvg pb0 = new Pvg("animalRegister/list", "羊只登记","",pb);
		Pvg pb1 = new Pvg("earTage/list", "耳标登记","",pb);
		Pvg pb2 = new Pvg("codeRegistration/list", "冻胚编码登记","",pb);
		Pvg pb3 = new Pvg("frozenEmbryorEgister/list", "冻胚登记","",pb);
		Pvg pb4 = new Pvg("#", "冻精登记","",pb);//frozenSemenEgister/list
		Pvg pc  = new Pvg("#", "销售管理", "fa-asterisk");
		Pvg pc0 = new Pvg("customer/list", "客户管理","",pc);
		Pvg pc1 = new Pvg("salesSlip/list", "羊只销售单","",pc);
		Pvg pc2 = new Pvg("salesInventory/list", "销售库存","",pc);
		Pvg pc3 = new Pvg("sold/list", "已售查询","",pc);
		Pvg pd  = new Pvg("#", "库存管理", "fa-line-chart");
		Pvg pd0 = new Pvg("sheepStock/list", "羊只库存","",pd);
		Pvg pd1 = new Pvg("embryoStock/list", "胚胎库存","",pd);
		Pvg p73 = new Pvg("house/list", "饲舍存栏", "",pd);
		Pvg pd2 = new Pvg("#", "冻精库存","",pd);
		Pvg pd3 = new Pvg("outboundCheck/listanimal", "死淘出库复核","",pd);
		Pvg pd5 = new Pvg("outboundCheck/list","销售出库复核","",pd);
		Pvg pd4 = new Pvg("sheepInventory/list", "羊只盘点","",pd);
		Pvg pe  = new Pvg("#", "绩效考核", "fa-star");
		Pvg pe1 = new Pvg("achievements/raise", "绩效考核","",pe);
		Pvg pf  = new Pvg("#", "系统设置", "fa-gears");
		Pvg pf0 = new Pvg("company/list", "集团信息","",pf);
		Pvg pf1 = new Pvg("organization/list", "分场管理","",pf);
		Pvg pf2 = new Pvg("user/list", "账号管理","",pf);
		//Pvg pf3 = new Pvg("#", "集团基础数据","",pf);
		Pvg pf30 = new Pvg("varieties/list", "羊只品种设置","",pf);
		Pvg pf31 = new Pvg("grading/list", "羊只销售定价","",pf);
		Pvg pf32 = new Pvg("embryoSalesPricing/list", "胚胎销售定价","",pf);
		Pvg pf33 = new Pvg("diseaseCauses/list", "疾病及淘汰原因","",pf);
		Pvg pf34 = new Pvg("breedEliminate/list", "育种淘汰原因","",pf);
		Pvg pf35 = new Pvg("deathReason/list", "死亡原因","",pf);
		Pvg pf36 = new Pvg("ramDefect/list", "公羊缺陷","",pf);
		Pvg pf37 = new Pvg("eweDefect/list", "母羊缺陷","",pf);
		Pvg pf38 = new Pvg("supplier/list", "供货商维护","",pf);
		Pvg p6a = new Pvg("breedParameter/list", "繁殖参数",  "",pf);
		Pvg pf4 = new Pvg("role/list", "角色管理","",pf);
		//Pvg fc1 = new Pvg("#","分厂基础设置","",pf);
		Pvg fc10 = new Pvg("embryoFrozencode/list","存储罐号","",pf);
		Pvg fc11 = new Pvg("fenceManagement/list", "栋栏管理", "",pf);
		Pvg fc12 = new Pvg("staffFile/list", "员工档案","",pf);
		Pvg fc13= new Pvg("timing/list","系统维护","",pf);
		Pvg pg = new Pvg("myGroup/list", "我的群组", "fa-users");
		Pvg ph = new Pvg("#", "筛选器", "fa-dropbox");
		Pvg papp1 = new Pvg("#", "消息通知");
		Pvg papp2 = new Pvg("#", "羊只信息");
		Pvg papp3 = new Pvg("#", "新戴标");
		Pvg papp4 = new Pvg("#", "补戴标");
		Pvg papp5 = new Pvg("#", "采精调教");
		Pvg papp6 = new Pvg("#", "配种");
		Pvg papp7 = new Pvg("#", "测孕");
		Pvg papp8 = new Pvg("#", "产羔");
		Pvg papp9 = new Pvg("#", "流产");
		Pvg papp10 = new Pvg("#", "断乳");
		Pvg papp11 = new Pvg("#", "定级");
		Pvg papp12 = new Pvg("#", "选配方案");
		Pvg papp13 = new Pvg("#", "称重");
		Pvg papp14 = new Pvg("#", "品相");
		Pvg papp15 = new Pvg("#", "体尺");
		Pvg papp16 = new Pvg("#", "缺陷登记");
		Pvg papp17 = new Pvg("#", "诊疗");
		Pvg papp18 = new Pvg("#", "疾病淘汰");
		Pvg papp19 = new Pvg("#", "育种淘汰");
		Pvg papp20 = new Pvg("#", "死亡");
		Pvg papp21 = new Pvg("#", "转栏");
		Pvg papp22 = new Pvg("#", "调拨");
		Pvg papp23 = new Pvg("#", "存栏");
		Pvg papp24 = new Pvg("#", "盘点");
		Pvg papp25 = new Pvg("#", "供体组群");
		Pvg papp26 = new Pvg("#", "受体组群");
		Pvg papp27 = new Pvg("#", "鲜胚移植");
		Pvg papp28 = new Pvg("#", "冻胚移植");
		Pvg papp29 = new Pvg("#", "销售");
		Pvg papp30 = new Pvg("#", "出库复核");
		Pvg papp31 = new Pvg("#", "群组");
		
		pvgRepository.save(returnList(p0,p1,p19,p10,p11,p12,p13,p14,p1a,p17,p15,p16,p1b,p18,p3,p30,p31,p32,p34,p35,p37,p38,p39,p310,p311,p312,p313,p401,p3a,
				p4,p40,p41,p42,p43,p5,p50,p51,p510,p511,p52,p53,p54,p55,p6,p60,p61,p621,p63,p630,p631,p64,p65,p66,p67,p68,p69,p6a,
				p71,p73,p8,p80,p81,p82,p85,p83,p84,p9,p90,p91,p92,p93,p94,pa,pa0,pa00,pa01,pb,pb0,pb1,pb2,pb3,pb4,pc,pc0,pc1,pc2,pc3,
				pd,pd0,pd1,pd2,pd3,pd5,pd4,pe,pe1,pf,pf0,pf1,pf2,pf4,pf30,pf31,pf32,pf33,pf34,pf35,pf36,pf37,pf38,pg,ph,papp1,
				papp2,papp3,papp4,papp5,papp6,papp7,papp8,papp9,papp10,papp11,papp12,papp13,papp14,papp15,papp16,papp17,papp18,papp19,papp20,
				papp21,papp22,papp23,papp24,papp25,papp26,papp27,papp28,papp29,papp30,papp31,fc13));
		
		pvgRepository.save(returnList(fc10,fc11,fc12,p402));
		return SUCCESS;
	}
	
	public static List<Pvg> returnList(Pvg... pvg){
		List<Pvg> plist = new LinkedList<Pvg>();
		for(Pvg p : pvg){
			plist.add(p);
		}
		return plist;
	}
	public List<Long> getIds(Iterable<Pvg> iterable){
		List<Long> ids = new LinkedList<Long>();
		for(Pvg p : iterable){
			ids.add(p.getId());
		}
		return ids;
	}
}
