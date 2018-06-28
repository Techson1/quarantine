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
import org.springframework.transaction.annotation.Transactional;

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.CodeRegister;
import com.beiqisoft.aoqun.entity.EarChange;
import com.beiqisoft.aoqun.repository.CodeRegisterRepository;
import com.beiqisoft.aoqun.repository.EarChangeRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.EarChangeService;
import com.beiqisoft.aoqun.util.MyUtils;

@Service
public class EarChangeServiceImpl extends BaseServiceIml<EarChange,EarChangeRepository> implements EarChangeService{

	@Autowired
	public EarChangeRepository earChangeRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public CodeRegisterRepository codeRegisterRepository;
	
	public Page<EarChange> find(final EarChange earChange) {
		return earChangeRepository.findAll(new Specification<EarChange>() {
			public Predicate toPredicate(Root<EarChange> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(earChange,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(earChange.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<EarChange> find(EarChange earChange, int size) {
		return earChangeRepository.findAll(new Specification<EarChange>() {
			public Predicate toPredicate(Root<EarChange> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public EarChangeRepository getRepository() {
		return earChangeRepository;
	}

	@Override
	public Message newVerify(BaseInfo base, String rfid) {
		if (base==null){
			return GlobalConfig.setAbnormal("该羊不存在");
		}
		if (base.getRfid()!=null && !"".equals(base.getRfid())){
			return GlobalConfig.setAbnormal("该羊电子耳号已存在不能进行新戴标");
		}
		CodeRegister code=codeRegisterRepository.findByVisualCode(MyUtils.strToLong(rfid));
		if (code==null){
			return GlobalConfig.setAbnormal("该羊的电子耳号不在耳标登记中存在,请更换电子耳号");
		}
		if (SystemM.PUBLIC_TRUE.equals(code.getUseState())){
			return GlobalConfig.setAbnormal("该电子耳号已使用,请更换电子耳标");
		}
		if (baseInfoService.findByCodeOrRfid(rfid)!=null){
			return GlobalConfig.setAbnormal("该电子耳号已使用,请更换电子耳标");
		}
		if (!base.getOrg().equals(code.getOrg())){
			return GlobalConfig.setAbnormal("该电子耳号不存在");
		}
//		if (!base.getBreed().equals(code.getBreed())){
//			return GlobalConfig.setAbnormal("该羊的品种与电子耳标的品种不一致,请更换电子耳号");
//		}
		return GlobalConfig.SUCCESS;
	}

	@Transactional
	@Override
	public BaseInfo newAdd(BaseInfo base, String rfid) {
		message = newVerify(base,rfid);
		if (!message.isCodeEqNormal()){
			return null;
		}
		baseInfoService.getRepository().save(base.setRfidReturnThis(rfid));
		codeRegisterRepository.save(
				codeRegisterRepository.findByVisualCode(MyUtils.strToLong(rfid))
					.setUseStateReturnThis(SystemM.PUBLIC_TRUE));
		return base;
	}

	@Transactional
	@Override
	public Message newDel(BaseInfo base, String rfid) {
		baseInfoService.getRepository().save(base.setRfidReturnThis(null));
		codeRegisterRepository.save(
				codeRegisterRepository.findByVisualCode(MyUtils.strToLong(rfid))
					.setUseStateReturnThis(SystemM.PUBLIC_FALSE));
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message changeVerify(BaseInfo base, String rfid) {
		if (base==null){
			return GlobalConfig.setAbnormal("该羊不存在");
		}
		if (base.getRfid()==null || "".equals(base.getRfid())){
			return GlobalConfig.setAbnormal("该羊电子耳号为空,应在新戴标中添加");
		}
		CodeRegister code=codeRegisterRepository.findByVisualCode(MyUtils.strToLong(rfid));
		if (code==null){
			return GlobalConfig.setAbnormal("该羊的电子耳号不在耳标登记中存在,请更换电子耳号");
		}
		if (SystemM.PUBLIC_TRUE.equals(code.getUseState())){
			return GlobalConfig.setAbnormal("该电子耳号已使用,请更换电子耳标");
		}
		if (baseInfoService.findByCodeOrRfid(rfid)!=null){
			return GlobalConfig.setAbnormal("该电子耳号已使用,请更换电子耳标");
		}
//		if (!base.getBreed().equals(code.getBreed())){
//			return GlobalConfig.setAbnormal("该羊的品种与电子耳标的品种不一致,请更换电子耳号");
//		}
		return GlobalConfig.SUCCESS;
	}
	/**
	 * cause:换标原因
	 * recoder:操作人
	 */
	@Override
	public EarChange changeAdd(BaseInfo base, String rfid, String cause,String recorder) {
		EarChange ear=new EarChange(base,rfid,cause,recorder);
		earChangeRepository.save(ear);
		baseInfoService.getRepository().save(base.setRfidReturnThis(rfid)); 
		codeRegisterRepository.save(
				codeRegisterRepository.findByVisualCode(MyUtils.strToLong(rfid))
					.setUseStateReturnThis(SystemM.PUBLIC_TRUE));
		return ear;
	}

}
