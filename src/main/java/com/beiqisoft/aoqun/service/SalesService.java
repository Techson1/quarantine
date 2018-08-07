package com.beiqisoft.aoqun.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Sales;
import com.beiqisoft.aoqun.repository.SalesRepository;

public interface SalesService extends BaseService<Sales, SalesRepository>{
	/**
	 * 分页获取用户对象
	 * @param sales 查询条件
	 * @return
	 */
	Page<Sales> find(Sales sales);
	
	Page<Sales> find(Sales sales, int pageNum);
	
	public Map<String,Object> findMapPage(final Sales sales);
	
}
