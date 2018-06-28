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
import com.beiqisoft.aoqun.entity.DonorGroup;
import com.beiqisoft.aoqun.entity.EmbryoProject;
import com.beiqisoft.aoqun.entity.Joining;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.repository.BaseInfoRepository;
import com.beiqisoft.aoqun.repository.DonorGroupRepository;
import com.beiqisoft.aoqun.repository.EmbryoProjectRepository;
import com.beiqisoft.aoqun.repository.JoiningRepository;
import com.beiqisoft.aoqun.repository.ParityRepository;
import com.beiqisoft.aoqun.repository.ReceptorGroupRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.DonorGroupService;

@Service
public class DonorGroupServiceImpl extends BaseServiceIml<DonorGroup,DonorGroupRepository> implements DonorGroupService{

	@Autowired
	public DonorGroupRepository donorGroupRepository;
	@Autowired
	public EmbryoProjectRepository embryoProjectRepository;
	@Autowired
	public BaseInfoRepository baseInfoRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public ParityRepository parityRepository;
	@Autowired
	public JoiningRepository joiningRepository;
	@Autowired
	public ReceptorGroupRepository receptorGroupRepository;
	
	public Page<DonorGroup> find(final DonorGroup donorGroup) {
		return donorGroupRepository.findAll(new Specification<DonorGroup>() {
			public Predicate toPredicate(Root<DonorGroup> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(donorGroup,root,criteriaBuilder);
				//根据项目id查询
				if (donorGroup.getProject()!=null){
					Join<EmbryoProject,EmbryoProjectRepository> join = root.join("project", JoinType.INNER);
					list.add(criteriaBuilder.equal(join.get("id"),donorGroup.getProject().getId()));
				}
				
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(donorGroup.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<DonorGroup> find(DonorGroup donorGroup, int size) {
		return donorGroupRepository.findAll(new Specification<DonorGroup>() {
			public Predicate toPredicate(Root<DonorGroup> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public DonorGroupRepository getRepository() {
		return donorGroupRepository;
	}

	@Override
	public Message addVerify(String code,Long projectId){
		BaseInfo dam = baseInfoService.findByCodeOrRfid(code);
		if (dam==null){
			return new Message(GlobalConfig.ABNORMAL,code+":该羊只不存在");
		}
		if (!SystemM.PUBLIC_SEX_DAM.equals(dam.getSex())){
			return new Message(GlobalConfig.ABNORMAL,code+":该羊性别错误");
		}
		if (!SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT.equals(dam.peelBreedingState())){
			return new Message(GlobalConfig.ABNORMAL,code+":只有羊只的繁殖状态为空怀才能供体组群,该羊的繁殖状态为:"
					+SystemM.baseInfoBreedingRturnChinese(dam.peelBreedingState()));
		}
		if (donorGroupRepository.findByBaseInfo_idAndProject_id(
				dam.getId(),projectId)!=null){
			return GlobalConfig.setAbnormal("该羊已在供体组群中,不能添加");
		}
		if(receptorGroupRepository.findByBaseInfo_idAndProject_id(dam.getId(), projectId)!=null){
			return GlobalConfig.setAbnormal("该羊已在受体组群中,不能删除");
		}
		if (!embryoProjectRepository.findOne(projectId).getBreed().equals(dam.getBreed())){
			return new Message(GlobalConfig.ABNORMAL,code+":羊只的品种必须与项目品种一致,该羊的品种为:"
					+dam.getBreed().getBreedName());
		}
		return GlobalConfig.SUCCESS;
	}
	
	@Transactional
	@Override
	public DonorGroup add(String code, Long projectId,String recorder) {
		// 供体添加
		DonorGroup donorGroup=new DonorGroup();
		BaseInfo baseInfo=baseInfoService.findByCodeOrRfid(code);
		Parity parity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE, baseInfo.getId());
		donorGroup.setBaseInfo(baseInfo);
		donorGroup.setParity(parity);
		donorGroup.setProject(embryoProjectRepository.findOne(projectId));
		donorGroup.setFlag(SystemM.PUBLIC_TRUE);
		donorGroup.setRecorder(recorder);
		donorGroupRepository.save(donorGroup);
		return donorGroup;
	}

	@Transactional
	public Message delete(Long id) {
		donorGroupRepository.delete(id);
		return GlobalConfig.SUCCESS;
	}

	@Transactional
	public Message delVerify(Long id) {
		DonorGroup donorGroup =donorGroupRepository.findOne(id);
		Joining joining=joiningRepository.findByDam_idAndProject_id(donorGroup.getBaseInfo().getId(), donorGroup.getProject().getId());
		if (joining!=null){
			return new Message(GlobalConfig.ABNORMAL,"不能删除该供体组群");
		}
		if (!SystemM.PUBLIC_TRUE.equals(donorGroup.getFlag())){
			return GlobalConfig.setAbnormal("该羊已复合,取消复合后才能删除");
		}
		return delete(id);
	}

	@Transactional
	@Override
	public void updateFlag(Long id, String flag) {
		DonorGroup donorGroup =donorGroupRepository.findOne(id);
		BaseInfo baseInfo=donorGroup.getBaseInfo();
		Parity parity=donorGroup.getParity();
		if (flag.equals(donorGroup.getFlag())) return;
		//通过审核修改羊只信息
		if (!SystemM.PUBLIC_TRUE.equals(flag)){
			//修改母羊
			baseInfo.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_DONOR_PREPARE);
			baseInfoService.getRepository().save(baseInfo);
			//修改胎次
			parity.setParityType(SystemM.PARITY_TYPE_DO);
			parity.setParityReceptorFresh(parity.getParityReceptorFresh()+1);
			parityRepository.save(parity);
		}
		//不通过审核回退羊只信息
		else{
			parity.setParityType(null);
			parity.setParityDonator(parity.getParityDonator()-1);
			parityRepository.save(parity);
			//修改母羊
			BaseInfo dam=donorGroup.getBaseInfo();
			dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT);
			baseInfoRepository.save(dam);
		}
		donorGroupRepository.save(donorGroup.setFlagRetuanThis(flag));
	}
	
}
