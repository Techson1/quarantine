package com.beiqisoft.aoqun.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.beiqisoft.aoqun.entity.CodeRegister;
import com.beiqisoft.aoqun.repository.BaseInfoRepository;
import com.beiqisoft.aoqun.repository.CodeRegisterRepository;
import com.beiqisoft.aoqun.service.CodeRegisterService;

@Service
public class CodeRegisterServiceImpl extends BaseServiceIml<CodeRegister,CodeRegisterRepository> implements CodeRegisterService{

	@Autowired
	public CodeRegisterRepository codeRegisterRepository;
	@Autowired
	public BaseInfoRepository baseInfoRepository;
	
	public Page<CodeRegister> find(final CodeRegister codeRegister) {
		return codeRegisterRepository.findAll(new Specification<CodeRegister>() {
			public Predicate toPredicate(Root<CodeRegister> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(codeRegister,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(codeRegister.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.ASC,"visualCode","code","ctime"));
	}
	
	public Page<CodeRegister> find(CodeRegister codeRegister, int size) {
		return codeRegisterRepository.findAll(new Specification<CodeRegister>() {
			public Predicate toPredicate(Root<CodeRegister> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public CodeRegisterRepository getRepository() {
		return codeRegisterRepository;
	}

	@Override
	public Message codeAddsVerify(CodeRegister codeRegister) {
		//校验电子耳标
		if (SystemM.CODE_TYPE_ELECTRONIC_EAR_TAG.equals(codeRegister.getType())){
			List<CodeRegister> codeRegisters=codeRegisterRepository.
					findByCodeIn(codeRegister.getCodes());
			List<BaseInfo> bases = baseInfoRepository.findByCodeIn(codeRegister.getCodes());
			//判读查询出的结果是否存在
			if (!codeRegisters.isEmpty()){
				 return new Message(GlobalConfig.ABNORMAL,"数据已存在,数据重"+codeRegisters.get(0).getCode()+"后开始重复");
			}
			if (!bases.isEmpty()){
				return GlobalConfig.setAbnormal("数据已使用,数据重"+bases.get(0).getCode()+"开始使用");
			}
			return GlobalConfig.SUCCESS;
		}
		//校验可视耳标
		else{
			List<CodeRegister> codeRegisters=codeRegisterRepository.
					findByVisualCodeGreaterThanEqualAndVisualCodeLessThanEqual(
							codeRegister.getVisualStartCode(), codeRegister.getVisualEndCode());
			
			List<BaseInfo> bases = baseInfoRepository.findByRfidIn(codeRegister.getRfids());
			//判读查询出的结果是否存在
			 if (!codeRegisters.isEmpty()){
				 return new Message(GlobalConfig.ABNORMAL,"数据已存在,数据重"+codeRegisters.get(0).getPrefixVisualCode()+"后开始重复");
			 }
			 if (!bases.isEmpty()){
				 return GlobalConfig.setAbnormal("该电子耳号系统已存在");
			 }
			 return GlobalConfig.SUCCESS;
		}
	}

	@Override
	public boolean codeAndRfidUseAmend(String code, String rfid) {
		if (code!=null && !"".equals(code)){
			CodeRegister codeRegister= codeRegisterRepository.findByCode(code);
			if (codeRegister!=null){
				codeRegisterRepository.save(codeRegister.setUseStateReturnThis(SystemM.PUBLIC_TRUE));
			}
		}
		if (rfid!=null && !"".equals(rfid)){
			CodeRegister rfidRegister= codeRegisterRepository.findByVisualCode(Long.parseLong(rfid));
			if (rfidRegister!=null){
				codeRegisterRepository.save(rfidRegister.setUseStateReturnThis(SystemM.PUBLIC_TRUE));
			} 
		}
		return false;
	}

	@Override
	public Map<String, Object> getCodeAndRfid(String state,Long breedId,String sex,Long orgId) {
		 Map<String,Object> map=new HashMap<String,Object>();
		 if (SystemM.CODE_BIRTH_STATE_NORMAL.equals(state)){
			 List<CodeRegister> codes=codeRegisterRepository
					 .findByStateAndTypeAndUseStateAndBreed_idAndSexAndOrg_idOrderByCode(
						state,SystemM.CODE_TYPE_ELECTRONIC_EAR_TAG,SystemM.PUBLIC_FALSE,breedId,sex,orgId);
				 List<CodeRegister> rfids=codeRegisterRepository
						 .findByStateAndTypeAndUseStateAndBreed_idAndSexAndOrg_idOrderByCode(
						 state,SystemM.CODE_TYPE_VISUAL_EAR_TAG,SystemM.PUBLIC_FALSE,breedId,sex,orgId);
				 map.put("code", codes.isEmpty()?null:codes.get(0).getCode());
				 map.put("rfid", rfids.isEmpty()?null:rfids.get(0).getVisualCode());
		 }
		 else{
			//TODO 会产生效率问题
			 List<CodeRegister> codes=codeRegisterRepository
					 .findByStateAndTypeAndUseStateAndSexAndOrg_idOrderByCode(
					state,SystemM.CODE_TYPE_ELECTRONIC_EAR_TAG,SystemM.PUBLIC_FALSE,sex,orgId);
			 List<CodeRegister> rfids=codeRegisterRepository
					 .findByStateAndTypeAndUseStateAndSexAndOrg_idOrderByCode(
					 state,SystemM.CODE_TYPE_VISUAL_EAR_TAG,SystemM.PUBLIC_FALSE,sex,orgId);
			 map.put("code", codes.isEmpty()?null:codes.get(0).getCode());
			 map.put("rfid", rfids.isEmpty()?null:rfids.get(0).getVisualCode());
		 }
		return map;
	}
}
