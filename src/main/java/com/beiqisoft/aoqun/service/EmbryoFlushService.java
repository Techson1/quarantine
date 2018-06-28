package com.beiqisoft.aoqun.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.EmbryoFlush;
import com.beiqisoft.aoqun.repository.EmbryoFlushRepository;

public interface EmbryoFlushService extends BaseService<EmbryoFlush, EmbryoFlushRepository>{
	/**
	 * 分页获取用户对象
	 * @param embryoFlush 查询条件
	 * @return
	 */
	Page<EmbryoFlush> find(EmbryoFlush embryoFlush);
	
	Page<EmbryoFlush> find(EmbryoFlush embryoFlush, int pageNum);

	/**
	 * 冲胚校验
	 * 一、校验耳号是否存在
	 * 二、校验该羊的繁殖状态是否为AI
	 * 三、校验该羊只是否在当前项目中
	 * 四、校验冲胚日期是否大于AI日期
	 * */
	Message addVerify(String damCode, Date date, Long projectId);

	/**
	 * 冲胚添加
	 * 一、冲胚添加
	 * 二、修改羊只信息
	 * */
	Message add(String damCode, EmbryoFlush embryoFlush);
	
	/**
	 * 冲胚修改校验
	 * 一、校验羊只繁殖状态是否为供体恢复
	 * */
	Message updateVerify(EmbryoFlush embryoFlush);

	/**
	 * 删除校验
	 * */
	Message delVerify(Long id);
	
	/**
	 * 删除
	 * */
	Message delete(EmbryoFlush embryoFlush);
	
}
