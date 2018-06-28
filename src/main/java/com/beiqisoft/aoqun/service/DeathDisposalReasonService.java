package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.DeathDisposalReason;
import com.beiqisoft.aoqun.repository.DeathDisposalReasonRepository;

public interface DeathDisposalReasonService extends BaseService<DeathDisposalReason, DeathDisposalReasonRepository>{
	/**
	 * 分页获取用户对象
	 * @param deathDisposalReason 查询条件
	 * @return
	 */
	Page<DeathDisposalReason> find(DeathDisposalReason deathDisposalReason);
	
	Page<DeathDisposalReason> find(DeathDisposalReason deathDisposalReason, int pageNum);
	
}
