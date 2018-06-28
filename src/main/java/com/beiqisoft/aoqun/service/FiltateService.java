package com.beiqisoft.aoqun.service;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.domain.FiltrateCondition;
import com.beiqisoft.aoqun.repository.BaseInfoRepository;

public interface FiltateService extends BaseService<BaseInfo, BaseInfoRepository>{

	List<Long> mainFrame(FiltrateCondition filtrateCondition);

}
