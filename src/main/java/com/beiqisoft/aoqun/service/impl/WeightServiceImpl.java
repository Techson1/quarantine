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
import com.beiqisoft.aoqun.entity.Weight;
import com.beiqisoft.aoqun.repository.WeightRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.WeightService;
import com.beiqisoft.aoqun.util.DateUtils;

@Service
public class WeightServiceImpl extends BaseServiceIml<Weight,WeightRepository> implements WeightService{

	@Autowired
	public WeightRepository weigthRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	
	public Page<Weight> find(final Weight wight) {
		return weigthRepository.findAll(new Specification<Weight>() {
			public Predicate toPredicate(Root<Weight> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(wight,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(wight.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "weighthDate"));
	}
	
	public Page<Weight> find(Weight wigth, int size) {
		return weigthRepository.findAll(new Specification<Weight>() {
			public Predicate toPredicate(Root<Weight> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public WeightRepository getRepository() {
		return weigthRepository;
	}

	@Override
	public Message addVerify(Weight weigth, String earTag,String type) {
		BaseInfo base=baseInfoService.findByCodeOrRfid(earTag);
		message = baseInfoService.flagVerify(base);
		if (!message.isCodeEqNormal()){
			return message;
		}
		if (!base.getOrg().getId().equals(weigth.getOrg().getId())){
			return GlobalConfig.setAbnormal("不是本分厂的羊,不能添加");
		}
		if (weigth.getId()==null){
			if(weigthRepository.findByBase_idAndWeighthDate(base.getId(),weigth.getWeighthDate())!=null){
				return GlobalConfig.setAbnormal("当天已经称过体重,不能再次称重");
			}
		}
		if (SystemM.WEIGHT_TYPE_WEANING.equals(type)){
			if(DateUtils.dateSubDate(weigth.getWeighthDate(), base.getBirthDay())>100){
				return GlobalConfig.setAbnormal("断奶日期不能大于出生日期100天");
			}
		}
		if (weigth.getId()==null){
			if(SystemM.WEIGHT_TYPE_WEANING.equals(type)){
				if(weigthRepository.findByBase_idAndType(
						base.getId(), SystemM.WEIGHT_TYPE_WEANING)!=null){
					return GlobalConfig.setAbnormal("该羊已添加过断奶重,不能重复添加");
				}
			}
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public void refresh(BaseInfo base) {
		List<Weight> weights = weigthRepository.findByBase_codeOrderByWeighthDateDesc(base.getCode());
		for (int i=0;i<weights.size();i++){
			if (i+1<weights.size()){
				weights.get(i).setAgeReturnThis(weights.get(i+1),base);
			}
		}
		weigthRepository.save(weights);
	}

	@Override
	public void addBirthWeight(BaseInfo baseInfo) {
		weigthRepository.save(new Weight().addBirthWeight(baseInfo));
	}
}
