package com.beiqisoft.aoqun.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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
import com.beiqisoft.aoqun.entity.EmbryoProject;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.entity.ReceptorGroup;
import com.beiqisoft.aoqun.repository.DonorGroupRepository;
import com.beiqisoft.aoqun.repository.EmbryoProjectRepository;
import com.beiqisoft.aoqun.repository.EmbryoTransplantRepository;
import com.beiqisoft.aoqun.repository.ParityRepository;
import com.beiqisoft.aoqun.repository.ReceptorGroupRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.ReceptorGroupService;

@Service
public class ReceptorGroupServiceImpl extends BaseServiceIml<ReceptorGroup,ReceptorGroupRepository> implements ReceptorGroupService{

	@Autowired
	public ReceptorGroupRepository receptorGroupRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public ParityRepository parityRepository;
	@Autowired
	public EmbryoProjectRepository embryoProjectRepository;
	@Autowired
	public EmbryoTransplantRepository embryoTransplantRepository;
	@Autowired
	public DonorGroupRepository donorGroupRepository;
	
	public Page<ReceptorGroup> find(final ReceptorGroup receptorGroup) {
		return receptorGroupRepository.findAll(new Specification<ReceptorGroup>() {
			public Predicate toPredicate(Root<ReceptorGroup> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(receptorGroup,root,criteriaBuilder);
				if (receptorGroup.getProject()!=null){
					Join<EmbryoProject,EmbryoProjectRepository> join = root.join("project", JoinType.INNER);
					list.add(criteriaBuilder.equal(join.get("id"), receptorGroup.getProject().getId()));
				}
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(receptorGroup.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<ReceptorGroup> find(ReceptorGroup receptorGroup, int size) {
		return receptorGroupRepository.findAll(new Specification<ReceptorGroup>() {
			public Predicate toPredicate(Root<ReceptorGroup> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public ReceptorGroupRepository getRepository() {
		return receptorGroupRepository;
	}

	@Override
	public ReceptorGroup add(String code, Long projectId,String recorder) {
		//供体羊保存
		ReceptorGroup receptorGroup=new ReceptorGroup();
		BaseInfo baseInfo=baseInfoService.findByCodeOrRfid(code);
		Parity parity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, baseInfo.getId());
		receptorGroup.setBaseInfo(baseInfo);
		receptorGroup.setParity(parity);
		receptorGroup.setProject(embryoProjectRepository.findOne(projectId));
		receptorGroup.setFlag(SystemM.PUBLIC_TRUE);
		receptorGroupRepository.save(receptorGroup);
		return receptorGroup;
	}

	@Override
	public Message addVerify(String code, Long projectId) {
		BaseInfo dam = baseInfoService.findByCodeOrRfid(code);
		if (dam==null){
			return new Message(GlobalConfig.ABNORMAL,code+":该羊只不存在");
		}
		if (!SystemM.PUBLIC_SEX_DAM.equals(dam.getSex())){
			return GlobalConfig.setAbnormal("该羊性别错误");
		}
		if (!SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT.equals(dam.peelBreedingState())){
			return new Message(GlobalConfig.ABNORMAL,code+":只有羊只的繁殖状态为空怀才能受体组群,该羊的繁殖状态为:"
					+SystemM.baseInfoBreedingRturnChinese(dam.peelBreedingState()));
		}
		if (!embryoProjectRepository.findOne(projectId).getBreed().equals(dam.getBreed())){
			return new Message(GlobalConfig.ABNORMAL,code+":羊只的品种必须与项目品种一致,该羊的品种为:"
					+dam.getBreed().getBreedName());
		}
		if (receptorGroupRepository.findByBaseInfo_idAndProject_id(
				dam.getId(),projectId)!=null){
			return GlobalConfig.setAbnormal("该羊已在受体组群中,不能添加");
		}
		if (donorGroupRepository.findByBaseInfo_idAndProject_id(
				dam.getId(),projectId)!=null){
			return GlobalConfig.setAbnormal("该羊已在供体组群中,不能添加");
		}
		return GlobalConfig.SUCCESS;
	}
	

	@Transactional
	@Override
	public void updateFlag(Long id, String flag) {
		ReceptorGroup receptorGroup=receptorGroupRepository.findOne(id);
		BaseInfo baseInfo=receptorGroup.getBaseInfo();
		Parity parity=receptorGroup.getParity();
		//通过审核修改羊只信息
		if (!SystemM.PUBLIC_TRUE.equals(flag)){
			//修改羊只
			baseInfo.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_RECEPTOR_PREPARE);
			baseInfoService.getRepository().save(baseInfo);
			//胎次修改
			parity.setParityType(SystemM.PARITY_TYPE_DO);
			parity.setParityDonator(parity.getParityDonator()+1);
			parityRepository.save(parity);
		}
		//不通过审核回退羊只信息
		else{
			parity.setParityType(null);
			parity.setParityDonator(parity.getParityReceptorFresh()-1);
			parityRepository.save(parity);
			//修改母羊
			baseInfo.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT);
			baseInfoService.getRepository().save(baseInfo);
		}
		receptorGroupRepository.save(receptorGroup.setFlagRetuanThis(flag));
	}

	@Override
	public Message delVerify(Long id) {
		ReceptorGroup receptorGroup=receptorGroupRepository.findOne(id);
		if (embryoTransplantRepository.findByReceptor_idAndProject_id(
				receptorGroup.getBaseInfo().getId(), receptorGroup.getProject().getId())!=null){
			return GlobalConfig.setAbnormal("该羊已移植不能删除");
		}
		if (!SystemM.PUBLIC_TRUE.equals(receptorGroup.getFlag())){
			return GlobalConfig.setAbnormal("该羊已通过审核,不能删除");
		}
		return delete(id);
	}

	@Transactional
	@Override
	public Message delete(Long id) {
		ReceptorGroup receptorGroup=receptorGroupRepository.findOne(id);
		
		Parity parity=receptorGroup.getParity();
		parity.setParityType(null);
		parity.setParityDonator(parity.getParityReceptorFresh()-1);
		parityRepository.save(parity);
		//修改母羊
		BaseInfo dam=receptorGroup.getBaseInfo();
		dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT);
		baseInfoService.getRepository().save(dam);
		//删除受体组群
		receptorGroupRepository.delete(id);
		return GlobalConfig.SUCCESS;
	}
}
