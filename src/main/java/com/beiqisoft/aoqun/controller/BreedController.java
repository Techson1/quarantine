package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.service.BreedService;

/**
 * 品种访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time  2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "breed")
public class BreedController extends BaseController<Breed,BreedService> {
	@RequestMapping(value ="list")
    public Page<Breed> list(Breed breed) throws InterruptedException{
		return page.pageAcquire(breedService.find(breed)).iteration(x -> {
			if (x.getBreedIds()!=null){
				String[] breedIds = x.getBreedIds().split(",");
				Long[] str2 = new Long[breedIds.length];
				for (int i = 0; i < breedIds.length; i++) {
					str2[i] = Long.valueOf(breedIds[i]);
				}
				String blood="";
				for (Breed b:breedService.getRepository().findByIdIn(str2)){
					blood=blood+b.getBreedName()+",";
				}
				x.setBlood(blood);
			}
		});
    }
	
	/**
	 * 查询纯种品种
	 * @exception InterruptedException
	 * @return List<Breed>
	 * */
	@RequestMapping(value ="breedType")
    public List<Breed> breedTypeList() throws InterruptedException{
		return breedService.getRepository().findByBreedType(SystemM.BREED_PUREBRED);
    }
	
	/**
	 * 品种添加
	 * 
	 * @param flag
	 * 			是否可用
	 * @param breedName
	 * 			品种名称
	 * @param breedType
	 * 			品种类型<br>
	 * 				纯种:15
	 * 				杂交:16
	 * 				杂种:17
	 * @param breedIds
	 * 			品种父id
	 * @param recorder
	 * 			记录人
	 * @return Message
	 * 			响应码:100,响应内容:操作成功
	 * */
	@RequestMapping(value ="add")
    public Message add(Breed breed){
		breedService.getRepository().save(breed.setBreedIdsAndSoft());
		return SUCCESS;
    }
	
	/**
	 * 根据id查询品种表
	 * 		如果品种是杂交,需要把杂交的父品种出放到breeds种
	 * @param id
	 * 			品种id
	 * @return Breed
	 * */
	@RequestMapping(value = "oneT/{id}")
	public Breed findOneT(@PathVariable Long id) {
		Breed breed=breedService.getRepository().findOne(id);
		breed.setBreedId(breed.getId());
		if (breed.getBreedIds()!=null && !"".equals(breed.getBreedIds())){
			for (String ids:breed.getBreedIds().split(",")){
				breed.getBreedsAdd().add(breedService.getRepository().findOne(Long.parseLong(ids)));
			}
		}
		return breed;
	}
	
	/**
	 * 校验血统
	 * 			如果品种类型为杂交则需要校验此项，否则不需要
	 * @param blood
	 * 			血统
	 * @return boolean
	 * 			如果系统不存在返回true,否则返回false
	 * */
	@RequestMapping(value ="bloodVerify")
	public boolean bloodVerify(String breedIds){
		return breedService.getRepository().findByBreedIds(breedIds)==null; 
	}
	
	/**
	 * 品种耳标校验
	 * */
	@RequestMapping(value = "breedNameVerify")
	public boolean breedNameVerify(String breedName,Long id){
		Breed breed = breedService.getRepository().findByBreedName(breedName);
		if (id == null && breed!=null) return false;
		if (breed!=null && !breed.getId().equals(id)) 
			return false;
		return true;
	}
	
}
