package com.beiqisoft.aoqun.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.LambingDam;
import com.beiqisoft.aoqun.repository.LambingDamRepository;

public interface LambingDamService extends BaseService<LambingDam, LambingDamRepository>{
	/**
	 * 分页获取用户对象
	 * @param lambingDam 查询条件
	 * @return
	 */
	Page<LambingDam> find(LambingDam lambingDam);
	
	Page<LambingDam> find(LambingDam lambingDam, int pageNum);

	/**
	 * 添加校验<BR>
	 * 一、判断羊只是否存在<BR>
	 * 二、判断胎次是否存在<BR>
	 * 三、查找最新胎次是否有测孕记录为已孕<BR>
	 * 四、判断生产日期与测孕日期是否大于妊娠日期<BR>
	 * 五、消息推送<BR>
	 * */
	Message addVerify(BaseInfo dam,Date bornDate,Long orgId);

	/**
	 * 添加<BR>
	 * 一、查找胎次记录<BR>
	 * 二、保存生产记录<BR>
	 * 三、修改胎次妊娠天数<BR>
	 * 四、修改羊只的繁殖状态为哺乳<BR>
	 * */
	Message add(LambingDam lambingDam, BaseInfo dam);

	/**
	 * 修改前校验<BR>
	 * 一、判断羊只繁殖状态是否为哺乳
	 * 二、判断羊只胎次是否关闭
	 * */
	Message editUiVerify(LambingDam lambingDam);

	/**
	 * 修改
	 * 一、修改生产记录
	 * 二、修改胎次的妊娠天数
	 * */
	LambingDam edit(LambingDam lambingDam);

	/**
	 * 生产删除校验
	 * */
	Message delVerify(Long id);
	
	/**
	 * 生产删除
	 * */
	Message delete(Long id);

	List<LambingDam> appList(String code, Date bornDate,Long orgId);
}
