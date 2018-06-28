package com.beiqisoft.aoqun.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.BreedingPlan;
import com.beiqisoft.aoqun.entity.Joining;
import com.beiqisoft.aoqun.repository.JoiningRepository;

public interface JoiningService extends BaseService<Joining, JoiningRepository>{
	
	/***/
	Page<Joining> findTest();
	
	/**
	 * 分页获取用户对象
	 * @param joining 查询条件
	 * @return
	 */
	Page<Joining> find(Joining joining);
	
	Page<Joining> find(Joining joining, int pageNum);
	
	/**
	 * 母羊配种添加校验
	 *	  一、判断羊只是否存
	 *   二、判断羊只性别
	 * 	  三、判断羊只状态
	 * 		1.羊只的繁殖状态只有在配种,空怀,未孕这三种状态下才能添加
	 * */
	Message joiningDamVerify(BaseInfo dam,String damCode,Long orgId);
	
	/**
	 * 配种校验
	 * 	 一、查询最新胎次
	 * 	 二、根据最新胎次查询出最新配种
	 * 		1.一只羊一生会有多个胎次,一个胎次会有多次配种,所以需要用胎次id来查找配种
	 * 	 三、判断配种日期
	 * 		1.如果最新配种不为空,那么比较，最新配种时间和传输过来的配种时间比较,如果最新配种小,则表示输入错误
	 * 		2.如果最新配种为空,并且胎次不空,那么表示该配种是当前胎次的第一次配种,判断配种时间和胎次开始时间
	 * */
	Message joiningVerify(BaseInfo dam,Date joiningDate);
	
	/**
	 * 血缘校验
	 *	 一、查询公羊
	 * 	 二、判断母羊与公羊是否为父女关系
	 * 	 三、判断母羊与公羊是否为母子关系
	 * 	 四、判断母羊与公羊是否为兄妹关系
	 * 		1.如果母羊大于公羊则是姐弟关系否则为兄妹关系
	 * */
	Message joiningBloodVerify(BaseInfo dam,BaseInfo sire);

	/**
	 * 配种修改前校验
	 * 	一、校验母羊是否存在
	 *  二、校验母羊繁殖状态是否为配种
	 *  三、校验该羊只胎次是否存在
	 *  四、校验该羊只胎次是否为最新胎次
	 *  五、判断该记录是否为最新配种
	 * */
	Message editUiVerify(Joining joining);

	/**
	 * 修改校验
	 * */
	Message editVerify(Joining joining, BaseInfo sire);
	
	/**
	 * 配种添加
	 * */
	Joining add(Joining joining, String damCode, String sireCode);

	/**
	 * 配种删除记录
	 * */
	Message delVerify(Long id);
	
	/**
	 * 配种删除
	 * */
	Message del(Long id);

	/**
	 * 添加AI校验
	 * */
	Message addAiVerify(String damCode, String sireCode, Date joiningDate,Long projectId);


	/**
	 * 添加AI
	 * */
	void addAi(String damCode, String sireCode, Date joniningDate,Long projectId);

	/**
	 * AI界面修改校验
	 * */
	Message updateUiAiVerify(Long id);

	/**
	 * Ai修改校验
	 * */
	Message updateAiVerifys(Long id, String sireCode, Date joiningDate);

	/**
	 * AI修改
	 * */
	Message updateAi(Long id, String sireCode, Date joiningDate);

	/**
	 * 公羊校验
	 * */
	Message joiningSireVerify(String sireCode,Long orgId);

	BreedingPlan isBreedingPlan(BaseInfo dam,BaseInfo sire);

}
