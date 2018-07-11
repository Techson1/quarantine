package com.beiqisoft.aoqun.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.mapping.Collection;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.BreedingWeed;
import com.beiqisoft.aoqun.entity.Contact;
import com.beiqisoft.aoqun.entity.DeathDisposal;
import com.beiqisoft.aoqun.entity.IllnessWeed;
import com.beiqisoft.aoqun.entity.LambingDam;
import com.beiqisoft.aoqun.entity.Looks;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.RankTest;
import com.beiqisoft.aoqun.entity.Weight;
import com.beiqisoft.aoqun.entity.domain.BaseInfoDetails;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.MyUtils;
import com.beiqisoft.aoqun.util.excel.ExportDate;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 羊只访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time  2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "baseInfo")
public class BaseInfoController extends BaseController<BaseInfo,BaseInfoService> {
	@JSON(type=Looks.class,filter="base")
	@JSON(type=LambingDam.class,filter="dam,parity,org")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=RankTest.class,include="name")
	@JSON(type=Organization.class,include="brief")
	@JSON(type=BaseInfo.class,filter="dam,sire")
	@RequestMapping(value ="list")
    public Page<BaseInfo> list(BaseInfo baseInfo) throws InterruptedException{
		baseInfo.setCtime(null);
		if (SystemM.FLAG!=baseInfo.getPhysiologyStatus()){
			baseInfo.setFlag(SystemM.PUBLIC_FALSE);
		}else{
			baseInfo.setPhysiologyStatus(null);
			baseInfo.setFlag(SystemM.PUBLIC_TRUE);
		}
		baseInfo.setMoonAge(null);
		
		//return baseInfoService.find(baseInfo);
		return page.pageAcquire(baseInfoService.find(baseInfo)).iteration(x -> {
			//x.setMoonAge(DateUtils.dateToAge(x.getBirthDay()));
			x.setDamCode(x.getDam()!=null?x.getDam().getCode():null);
			x.setSireCode(x.getSire()!=null?x.getSire().getCode():null);
		});
    }
	@JSON(type=Looks.class,filter="base")
	@JSON(type=LambingDam.class,filter="dam,parity,org")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=RankTest.class,include="name")
	@JSON(type=Organization.class,include="brief")
	@JSON(type=BaseInfo.class,filter="dam,sire")
	@RequestMapping(value ="listNotPage")
    public Page<BaseInfo> listNotPage(BaseInfo baseInfo) throws InterruptedException{
		baseInfo.setCtime(null);
		if (SystemM.FLAG!=baseInfo.getPhysiologyStatus()){
			baseInfo.setFlag(SystemM.PUBLIC_FALSE);
		}else{
			baseInfo.setPhysiologyStatus(null);
			baseInfo.setFlag(SystemM.PUBLIC_TRUE);
		}
		baseInfo.setMoonAge(null);
		//return baseInfoService.find(baseInfo);
		return page.pageAcquire(baseInfoService.find1(baseInfo)).iteration(x -> {
			//x.setMoonAge(DateUtils.dateToAge(x.getBirthDay()));
			x.setDamCode(x.getDam()!=null?x.getDam().getCode():null);
			x.setSireCode(x.getSire()!=null?x.getSire().getCode():null);
		});	
    }
	@JSON(type=BaseInfo.class,include="id,dam,sire,sex,code,rfid,state,birthDay,initialWeigh,looks")
	@RequestMapping(value = "one/{id}")
	public BaseInfo findOne1(@PathVariable Long id) {
		
		return getRepository().findOne(id);
	}
	@JSON(type=Looks.class,filter="base")
	@JSON(type=LambingDam.class,filter="dam,parity,org")
	@JSON(type=BaseInfo.class,filter="dam,sire")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=RankTest.class,include="name")
	@JSON(type=Organization.class,include="brief")
	@RequestMapping(value ="lambList")
    public Page<BaseInfo> lambList(BaseInfo baseInfo) throws InterruptedException{
		baseInfo.setCtime(null);
		//baseInfo.setFlag(SystemM.PUBLIC_FALSE);
		baseInfo.setMoonAge(null);
		return page.pageAcquire(baseInfoService.lambFind(baseInfo)).iteration(x -> {
			x.setDamCode(x.getDam()!=null?x.getDam().getCode():null);
			x.setSireCode(x.getSire()!=null?x.getSire().getCode():null);
			x.setLooks(looksService.getRepository().findByBase_id(x.getId()));
		});
    }
	
	/**
	 * 羊只档案导出
	 * */
	@RequestMapping(value ="export")
	public void export(HttpServletRequest request, HttpServletResponse response,BaseInfo baseInfo,int number) throws IOException{
		baseInfo.setFlag(SystemM.PUBLIC_FALSE);
		baseInfo.setCtime(null);
		baseInfo.setMoonAge(null);
		List<BaseInfo> baseInfos = baseInfoService.find(baseInfo,PAGE_SIZE*number).getContent();
		for (BaseInfo b:baseInfos){
			b.setBreedName(b.getBreed().getBreedName());
			b.setDamCode(b.getDam()!=null?b.getDam().getCode():"");
			b.setSireCode(b.getSire()!=null?b.getSire().getCode():"");
			b.setSex(SystemM.PUBLIC_SEX_SIRE.equals(b.getSex())?"公羊":"母羊");
			if(b.getRank()!=null&&b.getRank().getName()!=null) b.setRankName(b.getRank().getName());
			if(b.getPaddock().getName()!=null) b.setPaddockName(b.getPaddock().getName());
			if(b.getBreedingState()!=null) b.setBreedingState(breedingStateReturn(b.getBreedingState()));
			if(b.getPhysiologyStatus()!=null) b.setPhyCode(phyReturn(b.getPhysiologyStatus()));
			if(b.getGeneticLevel()!=null) b.setGeneticLevel(genetReturn(b.getGeneticLevel()));
 			if(b.getIsOutsourcing()!=null&&b.getIsOutsourcing().equals("1")) {
				if(b.getSourceType().equals("1")) {
					b.setSourceType("自产");
				}
				if(b.getSourceType().equals("2")){ 
					b.setSourceType("外购");
				}
			}else {
				b.setSourceType(b.getOrg().getBrief());
			} 
		}
		ExportDate.writeExcel("baseInfo",response,baseInfos,
				new String[]{"code","rfid","breedName","sex","birthDay","geneticLevel","moonAge","sireCode","damCode","bornStatus","rankName","paddockName","breedingState","phyCode","SourceType"},
				SystemM.PATH+UUID.randomUUID().toString()+".xls",
				new String[]{"可视耳号","电子耳号","品种","性别","出生日期","基因等级","月龄","父号","母号","生长阶段","定级","圈舍","繁殖状态","库存状态","来源"});
	}
	
	private String genetReturn(String geneticLevel) {
		if(geneticLevel.equals("2")) return "核心级";
        if(geneticLevel.equals("3")) return "后备核心级";
        if(geneticLevel.equals("4")) return "生产级";
        if(geneticLevel.equals("5")) return "销售级";
        if(geneticLevel.equals("6")) return "淘汰级";
        if(geneticLevel.equals("7")) return "受体级";
        return "";
	}
	private String phyReturn(Long phy) {
		String sys = "";
		if((""+phy).equals("1")) {
			sys= "在库";
		}
		if((""+phy).equals("2")) {
			sys= "疾病淘汰";
		}
		if((""+phy).equals("3")) {
			sys= "育种淘汰";
		}
		if((""+phy).equals("4")) {
			sys= "死亡";
		}
		if((""+phy).equals("5")) {
			sys= "疾病淘汰待审核";
		}
		if((""+phy).equals("6")) {
			sys= "育种淘汰待审核";
		}
		if((""+phy).equals("7")) {
			sys= "死亡待审核";
		}
		if((""+phy).equals("8")) {
			sys= "审核失败";
		}
		if((""+phy).equals("9")) {
			sys= "销售待审核";
		}
		if((""+phy).equals("10")) {
			sys= "销售审核";
		}
		if((""+phy).equals("11")) {
			sys= "调拨待审核";
		}
		if((""+phy).equals("12")) {
			sys= "调拨审核";
		}
		return sys;
	}
	private String breedingStateReturn(String state) {
		if(state.equals("--")) return "无状态";
        if(state.equals("10")) return "空怀";
        if(state.equals("111")) return "一配";
        if(state.equals("112")) return "二配";
        if(state.equals("113")) return "三配";
        if(state.equals("114")) return "四配";
        if(state.equals("115")) return "五配";
        if(state.equals("116")) return "六配";
        if(state.equals("117")) return "七配";
        if(state.equals("118")) return "八配";
        if(state.equals("119")) return "九配";
        if(state.equals("1110")) return "十配";
        if(state.equals("131")) return "未孕一";
        if(state.equals("132")) return "未孕二";
        if(state.equals("133")) return "未孕三";
        if(state.equals("134")) return "未孕四";
        if(state.equals("135")) return "未孕五";
        if(state.equals("136")) return "未孕六";
        if(state.equals("137")) return "未孕七";
        if(state.equals("138")) return "未孕八";
        if(state.equals("139")) return "未孕九";
        if(state.equals("1310")) return "未孕十";
        if(state.equals("141")) return "妊娠一";
        if(state.equals("142")) return "妊娠二";
        if(state.equals("143")) return "妊娠三";
        if(state.equals("144")) return "妊娠四";
        if(state.equals("145")) return "妊娠五";
        if(state.equals("146")) return "妊娠六";
        if(state.equals("147")) return "妊娠七";
        if(state.equals("148")) return "妊娠八";
        if(state.equals("149")) return "妊娠九";
        if(state.equals("1410")) return "妊娠十";
        if(state.equals("15")) return "哺乳";
        if(state.equals("20")) return "供体准备";
        if(state.equals("21")) return "受体准备";
        if(state.equals("22")) return "AI";
        if(state.equals("23")) return "供体恢复期";
        if(state.equals("24")) return "已移植";
        if(state.equals("25")) return "未调教";
        if(state.equals("26")) return "好";
        if(state.equals("27")) return "中";
        if(state.equals("28")) return "差";
        if(state.equals("29")) return "不爬羊";
        if(state.equals("30")) return "本交成功";
		return "";
	}
	/**
	 * 羊只档案
	 * */
	@JSON(type=BaseInfo.class,include="id,code,rfid,breed,sex,birthDay,initialWeigh,weaningWeight,dam,sire")
	@JSON(type=Breed.class,include="breedName")
	@RequestMapping(value="baseRecord")
	public BaseInfo baseRecord(String code){
		return baseInfoService.findByCodeOrRfid(code);
	}
	
	/**
	 * 羊只库存
	 * */
	@JSON(type=Contact.class,include="firstName")
	@JSON(type=Paddock.class,include="name,contact")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=RankTest.class,include="name")
	@JSON(type=Organization.class,include="orgName")
	@JSON(type=BaseInfo.class,include="code,rfid,breed,sex,birthDay,rank,bornStatus,paddock,moonAge,org")
	@RequestMapping(value ="inventory")
	public Page<BaseInfo> inventory(BaseInfo baseInfo) throws InterruptedException{
		baseInfo.setCtime(null);
		baseInfo.setFlag(SystemM.PUBLIC_FALSE);
		baseInfo.setPhysiologyStatus(MyUtils.strToLong(SystemM.NORMAL));
		return page.pageAcquire(baseInfoService.find(baseInfo))
				.iteration(x -> x.setMoonAge(DateUtils.dateToAge(x.getBirthDay())));
	}
	
	/**
	 * 销售库存
	 * */
	@JSON(type=Contact.class,include="firstName")
	@JSON(type=Paddock.class,include="name,contact")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=RankTest.class,include="name,rank,price")
	@JSON(type=Organization.class,include="orgName")
	@JSON(type=BaseInfo.class,include="code,rfid,breed,sex,birthDay,rank,bornStatus,paddock,moonAge,org,breedingState")
	@RequestMapping(value="market")
	public Page<BaseInfo> market(BaseInfo baseInfo) throws InterruptedException{
		baseInfo.setFlag(SystemM.PUBLIC_FALSE);
		baseInfo.setMoonAge(null);
		baseInfo.setPhysiologyStatus((long) 1);
		baseInfo.getRank().setRank("5");
		return page.pageAcquire(baseInfoService.market(baseInfo))
				.iteration(x -> x.setMoonAge(DateUtils.dateToAge(x.getBirthDay())));
	}
	/**
	 * 修改羊只基础信息表
	 * */
	@RequestMapping(value="update")
	public Message update(BaseInfo baseInfo){
		baseInfoService.getRepository().save(
				baseInfoService.getRepository().findOne(baseInfo.getId())
					.update(baseInfo));
		return SUCCESS;
	}
	
	/**
	 * 查询系谱
	 * */
	@JSON(type=BaseInfo.class,include="code,dam,sire")
	@RequestMapping(value="genealogy")
	public BaseInfo genealogy(String code){
		return baseInfoService.findByCodeOrRfid(code);
	}
	
	/**
	 * 羊只查询
	 * */
	@RequestMapping(value ="findList")
	public Page<BaseInfo> findList(BaseInfo baseInfo) throws InterruptedException{
		baseInfo.setFlag(SystemM.PUBLIC_FALSE);
		return baseInfoService.findList(baseInfo);
	}
	
	/**
	 * 羊只登记添加
	 * 	一、baseInfo添加
	 * 		1.根据前端传输数据自动封装对应
	 * 		2.设置isOutsourcing成员变量设置为1
	 * 		3.设置breedingState成员变量设置为--
	 * 		4.如果出生日期大于繁殖参数中的青年羊日龄,则为breedingState成员变量设置为空怀并且添加胎次
	 * 		5.如果父号或母号不存在则自动添加在baseInfo表中
	 * 	二、codeRegister修改
	 *      1.查找可视耳标和电子耳标后修改codeRegister实体类中的useState成员变量设置1
	 * 	三、血统添加:暂不实现
	 * @return Message
	 * */
	@RequestMapping(value ="registerAdd")
	public Message registerAdd(BaseInfo baseInfo){
		//一、baseInfo添加
		baseInfoService.baseInfoRegisteradd(baseInfo);
		//二、codeRegister修改
		codeRegisterService.codeAndRfidUseAmend(baseInfo.getCode(),baseInfo.getRfid());
		return SUCCESS;
	}
	
	/**
	 * 羊只批量添加
	 * */
	@RequestMapping(value ="registerAdds")
	public List<Message> registerAdds(BaseInfo baseInfo,String prefix,String codes,Integer number, Integer step,Long breedId){
		List<Message> messageList=new ArrayList<Message>();
		step=step==2?1:2;
		for (int i=0;i<number*step;i+=step){
			int start=Integer.parseInt(codes);
			String code=prefix+ MyUtils.complement(start+i,codes.length());
			Message message=baseInfoService.codeAndRfidVerify(code,null,breedId);
			message.setMsg(code+":"+message.getMsg());
			messageList.add(message);
			if (message.getCode()==101 || message.getCode()==102){
				continue;
			}
			baseInfo.setBreed(new Breed(breedId));
			//一、baseInfo添加
			baseInfoService.baseInfoRegisteradd(new BaseInfo(baseInfo,prefix+MyUtils.complement(start+i,codes.length())));
			//二、codeRegister修改
			codeRegisterService.codeAndRfidUseAmend(prefix+MyUtils.complement(start+i,codes.length()),baseInfo.getRfid());
		}
		return messageList;
	}
	
	/**
	 * 外购登记删除
	 * */
	@Transactional
	@RequestMapping(value="delOutsourcing/{id}")
	public Message delOutsourcing(@PathVariable Long id){
		Weight w=wigthService.getRepository().findByBase_idAndType(id, SystemM.WEIGHT_TYPE_INITIAL);
		if (w!=null){
			reviseWeightService.getRepository().delete(w.getId());
		}
		baseInfoService.getRepository().delete(id);
		return SUCCESS;
	}
	
	/**
	 * 耳号是否存在校验
	 * @param code
	 * 			可视耳号
	 * @return boolean 
	 * 				耳号不存在返回true,否则返回false
	 * */
	@RequestMapping(value ="notCodeVerify")
	public Message notCodeVerify(String code){
		//return baseInfoService.flagVerify(code).isCodeEqNormal();
		BaseInfo base= baseInfoService.findByCodeOrRfid(code);
		if(codeRegisterService.getRepository().findByCode(code)!=null){
			return GlobalConfig.setAbnormalTo("该耳号已在耳标库中存在,请更换耳号或添加到羊只库存中");
		}
		if (base == null){
			return GlobalConfig.setAbnormal("该羊不存在,是否添加");
		}
		
		return SUCCESS;
	}

	/**
	 * 校验
	 * */
	@RequestMapping(value="flagVerify")
	public Message flagVerify(String earTag){
		baseInfoService.flagVerify(earTag);
		return SUCCESS;
	}
	
	/**
	 * 羊只父辈添加
	 * */
	@RequestMapping(value ="parentAdd")
	public Message parentAdd(BaseInfo baseInfo){
		baseInfoService.getRepository().save(baseInfo);
		return SUCCESS;
	}
	
	/**
	 * 羊只父辈添加校验
	 * */
	@RequestMapping(value="parentAddVerify")
	public Message parentAddVerify(Date parentDate,Date birthDate){
		if (DateUtils.dateSubDate(parentDate, birthDate)<395){
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	/**
	 * 羊只登记添加前校验
	 * @param code
	 * 			可视耳号
	 * @param rfid
	 * 			电子耳号
	 * @param damCode
	 * 			母耳号
	 * @param sireCode
	 * 			父耳号
	 * @param sex
	 * 			性别
	 * @param breed.id
	 * 			品种id
	 * @param birthDay
	 * 			出生日期
	 * @return Message
	 * 			校验成功返回100，失败返回101及失败原因
	 * */
	@RequestMapping(value ="verify")
	public Message verify(BaseInfo baseInfo){
		return baseInfoService.verify(findByCode(baseInfo.getDamCode(),"2",null),
				findByCode(baseInfo.getSireCode(),"2",null),baseInfo);
	}
	
	/**
	 * 耳号校验
	 * @param code
	 * 			可视耳号
	 * @param rfid
	 * 			电子耳号
	 * @return Message
	 * 			校验成功返回100，失败返回101及失败原因
	 * */
	@RequestMapping(value="codeAndRfid")
	public Message codeAndRfid(String code,Long rfid,BaseInfo baseInfo){
		return baseInfoService.codeAndRfidVerify(code,rfid,baseInfo.getBreed().getId());
	}
	
	/**
	 * 查询耳标号
	 * 		为方便查询耳标查询
	 * @param code
	 * 			耳号
	 * @param type
	 * 			类型1归档查询2:普通查询
	 * @return BaseInfo	
	 * */
	@JSON(type=BaseInfo.class,include="code,rfid,breed,sex,birthDay,paddock")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="id,name")
	@RequestMapping(value ="findByCode")
	public BaseInfo findByCode(String code,String type,Long orgId){
		if (code==null || "".equals(code)){
			return null;
		}
		if (orgId!=null){
			return baseInfoService.getRepository().findByCodeAndFlagAndOrg_id(code,SystemM.PUBLIC_FALSE,orgId);
		}
		if (type.equals("1")){
			return baseInfoService.getRepository().findByCodeAndFlag(code,SystemM.PUBLIC_FALSE);
		}
		if (type.equals("2")){
			return baseInfoService.getRepository().findByCode(code);
		}
		return null;
	}
	
	/**
	 * 根据ids查询多个羊只
	 * @param ids
	 * 			多个id
	 * @return List<BaseInfo>
	 * */
	@JSON(type=BaseInfo.class,filter="sire,dam")
	@RequestMapping(value ="findByIds")
	public List<BaseInfo> findByIds(Long [] ids){
		return baseInfoService.getRepository().findByIdIn(ids);
	}
	
	/**
	 * 羔羊查询
	 * @param lambingDamId
	 * 			母羊id
	 */
	@JSON(type=Looks.class,filter="base")
	@JSON(type=LambingDam.class,filter="dam,parity,org")
	@RequestMapping(value ="findByLambingDam")
	public Page<BaseInfo> findByLambingDam(Long lambingDamId,Integer pageNum){
		return baseInfoService.findByLambingDam(lambingDamId,pageNum);
	}
	
	/**
	 * 羔羊添加校验
	 * */
	@RequestMapping(value ="lambSaveVerify")
	public Message lambSaveVerify(BaseInfo lamb){
		return baseInfoService.lambSaveVerify(lamb);
	}
	
	/**
	 * 羔羊添加
	 * */
	@RequestMapping(value="lambSave")
	public Message lambSave(BaseInfo baseInfo,String state){
		baseInfo.setBornStatus(state);
		return baseInfoService.lambSave(baseInfo);
	}
	
	/**
	 * 羔羊修改校验
	 * */
	@RequestMapping(value="lambEditVerify/{id}")
	public Message lambEditVerify(@PathVariable Long id){
		return baseInfoService.lambEditVerify(id);
	}
	
	/**
	 * 羔羊修改查询
	 * */
	@JSON(type=BaseInfo.class,filter="org,rank")
	@JSON(type=Looks.class,filter="base")
	@RequestMapping(value="findOne/{id}")
	public BaseInfo findOne(@PathVariable Long id){
		BaseInfo base=baseInfoService.getRepository().findOne(id);
		base.setLooks(looksService.getRepository().findByBase_id(base.getId()));
		return base;
	}
	
	
	/**
	 * 羔羊修改
	 * */
	@RequestMapping(value="lambEdit")
	public Message lambEdit(BaseInfo lamb){
		return baseInfoService.lambEdit(lamb);
	}
	
	/**
	 * 羊只删除
	 * */
	@RequestMapping(value="delBaseInfo/{id}")
	public Message delBaseInfo(@PathVariable Long id){
		//是否需要提示信息
		return SUCCESS;
	}
	
	/**
	 * 查询耳号是否存在
	 * @param earTag
	 * 			代表电子耳号和可视耳号
	 * @return 存在返回true不存在返回false
	 * */
	@RequestMapping(value="earTagVerify")
	public boolean earTagVerify(String earTag,Long orgId){
		BaseInfo base = baseInfoService.findByCodeOrRfid(earTag);
		if (base==null){
			return false;
		}
		if (base.getOrg()==null){
			return false;
		}
		if (!orgId.equals(base.getOrg().getId())){
			return false;
		}
		return baseInfoService.flagVerify(base).isCodeEqNormal();
		//return baseInfoService.findByCodeOrRfid(earTag)!=null;
	}
	
	/**
	 * 查询耳号是否与性别相同
	 * */
	@RequestMapping(value="earTagAndSexVerify")
	public boolean earTagAndSexVerify(String earTag,String sex){
		return baseInfoService.findByEarTagAndSex(earTag, sex);
	}
	
	/**
	 * 根据母羊产羔id及出生日期查询
	 * */
	@JSON(type=Looks.class,filter="base")
	@RequestMapping(value="appDamList")
	public List<BaseInfo> appDamList(Long id,Date birthDay){
		List<BaseInfo> base= baseInfoService.getRepository()
				.findByLambingDam_idAndBirthDayOrderByCtimeDesc(id,birthDay);
		for (BaseInfo b:base){
			b.setLooks(looksService.getRepository().findByBase_id(b.getId()));
		}
		return base;
	}
	
	
	/**
	 * 羔羊删除
	 * */
	@RequestMapping(value="lambDel/{id}")
	public Message lambDel(@PathVariable Long id){
		try{
			baseInfoService.lambDel(id);
		}catch(Exception e){
			return GlobalConfig.setAbnormal("数据存在关联,不能删除");
		}
		return SUCCESS;
	}
	
	/**
	 * 羊只信息
	 * */
	@JSON(type=BaseInfo.class,filter="")
	@RequestMapping(value="findByBaseInfo")
	public BaseInfoDetails findByBaseInfo(String earTag){
		BaseInfoDetails baseInfoDetails=new BaseInfoDetails();
		BaseInfo baseInfo=baseInfoService.findByCodeOrRfid(earTag);
		if (baseInfo!=null){
			baseInfoDetails.setBase(baseInfo);
			baseInfoDetails.setEmbryoReceptorNumber(
					embryoTransplantService.getRepository().findByReceptorCount(baseInfo.getId()));
			baseInfoDetails.setTransplantedEmbryonicNumber(
					embryoTransplantService.getRepository().findByReceptorTransNum(baseInfo.getId()));
			baseInfoDetails.setLambNumber(
					baseInfoService.getRepository().findByDamNumber(baseInfo.getId(),MyUtils.strToLong(SystemM.NORMAL)));
		}
		return baseInfoDetails;
	}
	
	/**
	 * 羊只复合查询
	 * */
	@JSON(type=Looks.class,filter="base")
	@JSON(type=LambingDam.class,filter="dam,parity,org")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=RankTest.class,include="name")
	@JSON(type=Organization.class,include="brief")
	@JSON(type=BaseInfo.class,filter="dam,sire")
	@RequestMapping(value="findByAudit")
	public Page<BaseInfo> findByAudit(BaseInfo baseInfo,Date startDeliveryDate,Date endDeliveryDate,String isAudit,String reviewing) 
			throws InterruptedException{
		baseInfo.setCtime(null);
		baseInfo.setFlag(SystemM.PUBLIC_FALSE);
		if (SystemM.PUBLIC_TRUE.equals(isAudit)){
			baseInfo.audit(baseInfo.getPhysiologyStatus()+"",reviewing);
		}
		else{
			baseInfo.notAudit(baseInfo.getPhysiologyStatus()+"");
		}
		return baseInfoService.findByAudit(baseInfo,startDeliveryDate,endDeliveryDate);
	}
	
	/**
	 * 羊只复合
	 * */
	@RequestMapping(value="audit/{id}/{type}/{reviewing}")
	public Message audit(@PathVariable Long id,@PathVariable String type,@PathVariable String reviewing){
		baseInfoService.getRepository().save(baseInfoService.getRepository().findOne(id).audit(type,reviewing));
		return SUCCESS;
	}
	
	/**
	 * 取消复合
	 * */
	@RequestMapping(value="notAudit/{id}/{type}")
	public Message notAudit(@PathVariable Long id,@PathVariable String type){
		baseInfoService.getRepository().save(baseInfoService.getRepository().findOne(id).notAudit(type));
		return SUCCESS;
	}
	
	/**
	 * 羊只复合接口校验 1
	 * */
	@RequestMapping(value="appOrgVerify")
	public Message appOrgVerify(String earTag,Long orgId,String type){
		BaseInfo base = baseInfoService.findByCodeOrRfid(earTag);
		if (!base.getOrg().getId().equals(orgId)){
			return GlobalConfig.setAbnormal("该羊不存在");
		}
		return SUCCESS;
	}
	
	/**
	 * app羊只复合校验，2 出库复合校验
	 * @param code 耳号
	 * @param type 出库类型
	 * */
	@RequestMapping(value="appAuditVerify")
	public Message appAuditVerify(String code,String type){
		Long physiologyStatus = MyUtils.strToLong(SystemM.type(type));
		BaseInfo base = baseInfoService.getRepository().findByAppAudit(code,code,physiologyStatus);
		if (base==null){
			return GlobalConfig.setAbnormal("该羊出库类型有误");
		}
		if (base.getOrg()==null){
			return GlobalConfig.setAbnormal("该羊不存在");
		}
		return GlobalConfig.setNormal(""+base.getId());
	}
	
	/**
	 * app羊只复合，3 出库复合接口
	 * @param id 羊只id
	 * @param type 出库类型
	 * @param reviewing 复合人
	 * @return SUCCESS
	 * */
	@JSON(type=BaseInfo.class,include="id,code,sex,breed,birthDay,isAudit,physiologyStatus")
	@RequestMapping(value="appAudit")
	public BaseInfo appAudit(Long id,String type,String reviewing){
		BaseInfo base=baseInfoService.getRepository().findOne(id).audit(type,reviewing);
		baseInfoService.getRepository().save(base);
		return base;
	}
	
	/**
	 * app取消羊只复合
	 * @param id 羊只id
	 * @param type 出库类型
	 * @param reviewing 复合人
	 * */
	@RequestMapping(value="appNotAudit")
	public BaseInfo appNotAudit(Long id,String type){
		BaseInfo base=baseInfoService.getRepository().findOne(id).notAudit(type);
		baseInfoService.getRepository().save(base);
		return base;
	}
	
	/**
	 * 羊只明细查询
	 * */
	@JSON(type=BaseInfo.class,filter="org,paddock,customer,source,lambingDam,ewes,fosterDam")
	@JSON(type=RankTest.class,include="name")
	@JSON(type=Breed.class,include="breedName")
	@RequestMapping(value="baseDisposal")
	public Map<String,Object> baseDisposal(String code){
		Map<String,Object> baseMap = new HashMap<String, Object>();
		BaseInfo base = baseInfoService.findByCodeOrRfid(code);
		baseInfoService.findByGenealogy(baseMap,base);
		return baseMap;
	}
	@RequestMapping("updateLamb")
	public Message updateLamb(BaseInfo entity) throws Exception {
		Looks looks = (Looks) reflectionUpdate(entity.getLooks(), looksService.getRepository());
		looksService.getRepository().save(looks);
		BaseInfo baseinfo = (BaseInfo) reflectionUpdate(entity, getRepository());
		getRepository().save(baseinfo);
		return SUCCESS;
	}
	/*@RequestMapping("shenhe")
	public Message shenhe() {
		//疾病
		List<Long> codes = null;
		codes.add(2L);
		List<BaseInfo> ills = baseInfoService.getRepository().findByPhysiologyStatusIn(2L,5L);
		for(BaseInfo a:ills) {
			List<IllnessWeed> ill = illnessWeedService.getRepository().findByBase_id(a.getId());
			if(ill.isEmpty()) {
				System.err.println("疾病表沒有id:"+a.getId());
			}
		}
		//疾病
		List<BaseInfo> breeds = baseInfoService.getRepository().findByPhysiologyStatusIn(3L,6L);
		for(BaseInfo b:breeds) {
			List<BreedingWeed> breeding = breedingWeedService.getRepository().findByBase_id(b.getId());
			if(breeding.isEmpty()) {
				System.err.println("育種表沒有id:"+b.getId());
			}
		}
		//疾病
		List<BaseInfo> deaths = baseInfoService.getRepository().findByPhysiologyStatusIn(4L,7L);
		for(BaseInfo c:deaths) {
			List<DeathDisposal> death = deathdisposalService.getRepository().findByBase_id(c.getId());
			if(death.isEmpty()) {
				System.err.println("死亡表沒有id:"+c.getId());
			}
		}
		return SUCCESS;
	}*/
}