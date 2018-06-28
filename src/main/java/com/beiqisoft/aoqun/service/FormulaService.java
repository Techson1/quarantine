package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Formula;
import com.beiqisoft.aoqun.repository.FormulaRepository;

public interface FormulaService extends BaseService<Formula, FormulaRepository>{
	/**
	 * 分页获取用户对象
	 * @param formula 查询条件
	 * @return
	 */
	Page<Formula> find(Formula formula);
	
	Page<Formula> find(Formula formula, int pageNum);
	
}
