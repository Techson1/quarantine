package com.beiqisoft.aoqun.service;

import java.util.Date;
import java.util.List;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.GeneralVeternary;
import com.beiqisoft.aoqun.entity.Joining;
import com.beiqisoft.aoqun.entity.domain.MessageEntity;
import com.beiqisoft.aoqun.repository.JoiningRepository;

public interface MessageService extends BaseService<Joining, JoiningRepository>{

	/**
	 * 空怀配种
	 * @param orgId
	 * 			分厂id
	 * @param code
	 * 			耳号
	 * @param paddockId
	 * 			圈舍
	 * @return
	 */
	List<MessageEntity> findByJoingingList(Long orgId,String code,Long paddockId);
	
	/**
	 * 未孕再配
	 * @param orgId
	 * 			分厂id
	 * @param code
	 * 			耳号
	 * @param paddockId
	 * 			圈舍
	 * @return
	 */
	List<MessageEntity> findByNotJoingingList(Long orgId, String code, Long paddockId);

	/**
	 * 测孕
	  * @param orgId
	 * 			分厂id
	 * @param code
	 * 			耳号
	 * @param paddockId
	 * 			圈舍
	 * @return
	 */
	List<MessageEntity> findByPregnancyList(Long orgId, String code, Long paddockId);

	/**
	 * 产羔
	 * @param orgId
	 * 			分厂id
	 * @param code
	 * 			耳号
	 * @param paddockId
	 * 			圈舍
	 * @return
	 */
	List<MessageEntity> findByLambingDamList(Long orgId, String code, Long paddockId);

	/**
	 * 断奶
	 * @param orgId
	 * 			分厂id
	 * @param code
	 * 			耳号
	 * @param paddockId
	 * 			圈舍
	 * @return
	 */
	List<MessageEntity> findByWeaningList(Long orgId, String code, Long paddockId);

	/**
	 * 
	 * 久配不孕
	 * @param orgId
	 * 			分厂
	 * @param pageNum
	 * 			页数
	 * @param predictDay
	 * 			久配天数
	 * @param code
	 * 			耳号
	 * @param paddockId
	 * 			圈舍
	 * @return
	 */
	List<BaseInfo> findByinfertilityWarning(Long orgId, Integer pageNum,Integer predictDay,String code,Long paddockId);

	/**
	 * 定级
	 * @param orgId
	 * 			分厂
	 * @param predictDay
	 * 			定级天数
	 * @param code
	 * 			耳号
	 * @param paddockId
	 * 			圈舍
	 * @return
	 */
	List<BaseInfo> findByRankHint(Long orgId,Date day,String code, Long paddockId);

	
	/**
	 * 疾病诊疗提示
	 * @param orgId
	 * 			分厂id
	 * @param code
	 * 			耳号
	 * @param paddockId
	 * 			圈舍
	 * @return
	 */
	List<GeneralVeternary> findByGeneralHint(Long orgId, String code, Long paddockId);

	/**
	 * 采精提示
	 * */
	List<BaseInfo> findBySemenHint(Long orgId, String code, Long paddockId);
}
