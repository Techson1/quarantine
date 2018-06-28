package com.beiqisoft.aoqun.service.impl;

import java.util.List;import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.domain.FiltrateCondition;
import com.beiqisoft.aoqun.repository.BaseInfoRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.FiltateService;

@Service
public class FiltateServiceImpl extends BaseServiceIml<BaseInfo,BaseInfoRepository> implements FiltateService{

	@PersistenceContext  
	private EntityManager em;
	@Autowired
	BaseInfoService baseInfoService;

	@Override
	public BaseInfoRepository getRepository() {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> mainFrame(FiltrateCondition filtrateCondition) {
		String hql = "SELECT b.id FROM BaseInfo b "+filtrateCondition.baseInfoForm("b")+" WHERE 1=1 "+filtrateCondition.baseInfoQuery("b");
		List<Long> list = em.createQuery(hql).getResultList();
		Looks(filtrateCondition).forEach(x->{
			System.out.println(x);
		});
		return list.stream()
				//.filter(x-> )
				.collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	private List<Long> Looks(FiltrateCondition filtrateCondition){
		System.out.println("looks");
		String hql = "SELECT l.base.id FROM Looks l WHERE l=1 "+filtrateCondition.looksQuery("l");
		System.out.println(hql);
		return em.createQuery(hql).getResultList();
	}
	
}