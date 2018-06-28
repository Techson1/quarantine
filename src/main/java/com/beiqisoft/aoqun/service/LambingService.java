package com.beiqisoft.aoqun.service;
import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Lambing;
import com.beiqisoft.aoqun.repository.LambingRepository;

/**
 * 羔羊生产业务处理接口类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 * @deprecated
 */
public interface LambingService extends BaseService<Lambing, LambingRepository>{
	/**
	 * 分页获取用户对象
	 * @param lambing 查询条件
	 * @return
	 */
	Page<Lambing> find(Lambing lambing);
	
	Page<Lambing> find(Lambing lambing, int pageNum);
	
}
