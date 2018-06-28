package com.beiqisoft.aoqun.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.InventoryDetail;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.repository.InventoryDetailRepository;
import com.beiqisoft.aoqun.repository.InventoryRepository;
import com.beiqisoft.aoqun.repository.PaddockRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.InventoryDetailService;
import com.beiqisoft.aoqun.service.PaddockChangeService;

@Service
public class InventoryDetailServiceImpl extends BaseServiceIml<InventoryDetail,InventoryDetailRepository> implements InventoryDetailService{

	@Autowired
	public InventoryDetailRepository inventoryDetailRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public PaddockRepository paddockRepository;
	@Autowired
	public PaddockChangeService paddockChangeService;
	@Autowired
	public InventoryRepository inventoryRepository;
	
	public Page<InventoryDetail> find(final InventoryDetail inventoryDetail) {
		return inventoryDetailRepository.findAll(new Specification<InventoryDetail>() {
			public Predicate toPredicate(Root<InventoryDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(inventoryDetail,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(inventoryDetail.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<InventoryDetail> find(InventoryDetail inventoryDetail, int size) {
		return inventoryDetailRepository.findAll(new Specification<InventoryDetail>() {
			public Predicate toPredicate(Root<InventoryDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public InventoryDetailRepository getRepository() {
		return inventoryDetailRepository;
	}

	@Override
	public Message verify(String code, Long paddockId, Long orgId) {
		BaseInfo base= baseInfoService.findByCodeOrRfid(code);
		if (base==null){
			return GlobalConfig.setAbnormal("该羊不存在");
		}
		if (!orgId.equals(base.getOrg().getId())){
			return GlobalConfig.setAbnormal("该羊不在本场");
		}
		if (!SystemM.NORMAL.equals(base.getPhysiologyStatus()+"")){
			return GlobalConfig.setAbnormal("该羊已出库");
		}
		if (inventoryDetailRepository.findByBase_id(base.getId())!=null){
			return GlobalConfig.setAbnormal("该羊已盘点");		
		}
//		if (!paddockId.equals(base.getPaddock().getId())){
//			return GlobalConfig.setIsPass("该羊圈舍与盘点圈舍不同,是否转圈?");
//		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public InventoryDetail add(String code, Long paddockId,Long inventoryId,String recorder) {
		BaseInfo base = baseInfoService.findByCodeOrRfid(code);
		InventoryDetail inventoryDetail = inventoryDetailRepository.findByBase_id(base.getId());
		if(inventoryDetail==null) inventoryDetail = new InventoryDetail();
		Paddock paddock = paddockRepository.findOne(paddockId);
		
		//保存
		inventoryDetail.setInventory(inventoryRepository.findOne(inventoryId));
		inventoryDetail.setBase(base);
		inventoryDetail.setFromPaddock(base.getPaddock());
		inventoryDetail.setToPaddock(paddock);
		inventoryDetailRepository.save(inventoryDetail);
		
		//添加转栏记录
		paddockChangeService.add(paddock, code, base.getOrg(), recorder);
		
		return inventoryDetail;
	}

}
