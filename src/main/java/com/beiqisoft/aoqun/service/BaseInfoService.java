package com.beiqisoft.aoqun.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.OnHandMonth;
import com.beiqisoft.aoqun.repository.BaseInfoRepository;

public interface BaseInfoService extends BaseService<BaseInfo, BaseInfoRepository>{
	/**
	 * 分页获取用户对象
	 * @param baseInfo 查询条件
	 * @return
	 */
	Page<BaseInfo> find(BaseInfo baseInfo);
	
	Page<BaseInfo> find(BaseInfo baseInfo, int pageNum);
	
	/**
	 * 羔羊查询
	 * */
	Page<BaseInfo> lambFind(BaseInfo baseInfo);
	
	/**
	 * 羊只登记添加
	 * 	一、baseInfo添加
	 * 		1.根据前端传输数据自动封装对应
	 * 		2.设置isOutsourcing成员变量为1
	 * 		3.设置breedingState成员变量为--
	 * 		4.如果出生日期大于繁殖参数中的青年羊日龄,则为breedingState成员变量为10
	 * 		5.如果父号或母号不存在则自动添加在baseInfo表中
  	 */
	boolean baseInfoRegisteradd(BaseInfo baseInfo);

	/**
	 * 羊只添加前校验
	 *  一、
	 *  	校验父号母号是否存在,如果不存在，则不需要校验可以直接添加
	 *  二、
	 *  	1.如果母号存在父号不存,则校验母号的出生日期是否大于该羊只的出生日期395天（13月龄）
	 *  		如果小于那么提示用户：“母羊月龄必须大于自身13个月”
	 *  	2.校验该羊只的品种与母羊的品种是否相同，如果不同则提示用户：“该羊的品种必须和母羊品种一致,母羊的品种为：”
	 *  三、
	 *  	1.判读父号是否存在于第二条类似
	 *  四、
	 *  	1.如果父号母号都存在,判断父号和母号是否都大于395天
	 *  	2.判断品种
	 *  		1).父号品种和母号品种相同则返回母号品种与该羊品种进行判断是否相同,不相同返回：母羊品种，公羊品种，
	 *  			该羊应该选择的品种
	 *  		2).如果父号母号品种不相同,判断血统并根据血统查询品种,如果没在找到品种说明该品种是杂种（杂种只有一个）
	 *  			a).如果一个品种的血统包含另一个血统，则返回血统较大的一项
	 *  			b).如果两个血统直接不包含，则返回连个血统直接的和
	 *  		3)判断该羊与第2).条返回的品种对比如果不相同返回：母羊品种，公羊品种，该羊应该选择的品种
	 * */
	Message verify(BaseInfo dam, BaseInfo sire, BaseInfo baseInfo);

	/**
	 * 判断可视耳号与电子耳号
	 * 	一、
	 * 		判断该可视耳号与电子耳标是否存在BaseInfo表中使用
	 *  二、
	 *  	判断该可视耳标与电子耳标是否在CodeRegister中存在
	 *  三、
	 *  	判读可视耳标与电子耳标是否已用
	 *  四、(暂时废弃)
	 *  	判断可视耳标、电子耳标及羊只品种id是否相同
	 * */
	Message codeAndRfidVerify(String code,Long rfid,Long breedId);

	Page<BaseInfo> findList(BaseInfo baseInfo);
	
	/**
	 * 查询电子耳号或可视耳号
	 * @param code 
	 * 			可输入电子耳号或可视耳号
	 * @return BaseInfo
	 * */
	BaseInfo findByCodeOrRfid(String code);
	
	/**
	 * 通过可视耳号查询，返回具体羊只
	 * @param code
	 * @return
	 */
	BaseInfo findByCode(String code);
	/**
	 * 通过电子耳号查询羊只
	 * @param rfid
	 * @return
	 */
	BaseInfo findByRfid(String rfid);
	
	/**
	 * 可视耳号 或者电子耳号，增加场区区分
	 * @param code
	 * @param orgId
	 * @return
	 */
	BaseInfo findByCodeOrRfidAndOrgId(String code,Long orgId);

	/**
	 * 羔羊添加校验
	 * 一、
	 * 		判读该羊
	 * */
	Message lambSaveVerify(BaseInfo lamb);
	
	/**
	 * 羔羊添加
	 * */
	Message lambSave(BaseInfo baseInfo);

	Page<BaseInfo> findByLambingDam(Long lambingDamId,Integer page);

	/**
	 * 校验羊只存在及性别
	 * */
	boolean findByEarTagAndSex(String earTag, String publicSexSire);

	/**
	 * 羔羊删除
	 * */
	Message lambDel(Long id);

	/**
	 * 羔羊修改校验
	 * */
	Message lambEditVerify(Long id);

	/**修改*/
	Message lambEdit(BaseInfo lamb);

	/**
	 * 查询销售库存
	 * */
	Page<BaseInfo> market(BaseInfo baseInfo);

	/**
	 * 耳号校验
	 * */
	Message codeVerify(String code);

	/**
	 * 羊只复合列表查询
	 * */
	Page<BaseInfo> findByAudit(BaseInfo baseInfo, Date startDeliveryDate,
			Date endDeliveryDate);
	/**
	 * 校验
	 */
	Message flagVerify(String earTag);

	/**
	 * 不查询校验
	 */
	Message flagVerify(BaseInfo base);

	/**
	 * 查询系谱及羊只明细
	 */
	void findByGenealogy(Map<String, Object> baseMap,BaseInfo base);
	
	/**
	 * 
	 */
	void moonAgeEdit(String code,Date date);

	Page<BaseInfo> find1(BaseInfo base);

	List<BaseInfo> find2(BaseInfo base);

	//Integer testFirstNum(Long id, String sex, Date startDate);

}
