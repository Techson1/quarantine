package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.EmbryoTransplant;
import com.beiqisoft.aoqun.repository.EmbryoTransplantRepository;

public interface EmbryoTransplantService extends BaseService<EmbryoTransplant, EmbryoTransplantRepository>{
	/**
	 * 分页获取用户对象
	 * @param embryoTransplant 查询条件
	 * @return
	 */
	Page<EmbryoTransplant> find(EmbryoTransplant embryoTransplant);
	
	Page<EmbryoTransplant> find(EmbryoTransplant embryoTransplant, int pageNum);

	/**
	 * 鲜胚移植受体羊修改校验
	 */
	Message updateVerify(String code,Long id);

	/**
	 * 绑定耳号
	 */
	Message bindingCode(String code,Long id);

	/**
	 * 鲜胚移植删除校验
	 * */
	Message delVerify(Long id);
	
	/**
	 * 鲜胚移植删除
	 * */
	Message delete(EmbryoTransplant embryoTransplant);
	
	/**
	 * 修改判断,如果找到与移植序号和冲胚id像匹配的羊只,则修改该记录
	 * @return 修改成功返回false 修改失败返回true
	 * */
	boolean deitJudge(String sheetCode, Long embryoFlushId,String qualityGrade,Integer transNum,String recorder);
}
