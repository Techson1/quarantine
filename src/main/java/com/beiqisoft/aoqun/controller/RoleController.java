package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.Pvg;
import com.beiqisoft.aoqun.entity.Role;
import com.beiqisoft.aoqun.service.RoleService;

/**
 * 角色访问控制类
 * @author 王栋
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "role")
public class RoleController extends BaseController<Role,RoleService> {
	
	/**
	 * 根据Type查询角色
	 * */
	@RequestMapping(value ="findByType")
	public List<Role> findByType(Integer type){
		return roleService.getRepository().findByType(type);
	}
	
	/**
	 * 角色初始化
	 * */
	@RequestMapping(value ="init")
	public Message init(){
		List<Pvg> list = (List<Pvg>) pvgService.getRepository().findAll();
		String[] pvgs=new String[]{"繁殖参数","集团信息","分场管理","羊只品种设置","羊只销售定价","胚胎销售定价","疾病及淘汰原因","育种淘汰原因","死亡原因","公羊缺陷","母羊缺陷","供货商维护"};
		for (int i=0;i<list.size();i++){
			for (String s:pvgs){
				if(list.get(i).getName().equals(s)){
					list.remove(i);
				}
			}
		}
		Role role =new Role();
		role.setName("虚拟");
		role.setPvgs(list);
		roleService.getRepository().save(role);
		return SUCCESS;
	}
	
	/**
	 * 角色列表，查询全部
	 * */
	@RequestMapping(value ="list")
    public Page<Role> list(Role role) throws InterruptedException{
		return roleService.find(role);
    }
	
	@RequestMapping(value="findByList")
	public List<Role> findByList(Role role)throws InterruptedException{
		return roleService.findByList(role);
	}
	
	/**
	 * 角色名称校验
	 * @param roleName
	 * 			角色名称
	 * */
	@RequestMapping(value ="roleNameVerify")
	public boolean roleNameVerify(String roleName,Long id){
		Role role = roleService.getRepository().findByNameV(roleName);
		if (id == null && role!=null) return false;
		if (role!=null && !role.getId().equals(id)) 
			return false;
		return true;
	}
	
	/**
	 * 角色授权
	 * @param roleId
	 * 			角色id
	 * @param pvgIds
	 * 			权限id集，每个权限用“,”分割
	 * @deprecated
	 * */
	@RequestMapping(value ="allotPvg")
	public Message allotPvg(Long roleId,String pvgIds){
		Role role=roleService.getRepository().findOne(roleId);
		List<Pvg> pvgs=new ArrayList<Pvg>();
		for (String s:pvgIds.split(",")){
			pvgs.add(new Pvg(Long.parseLong(s)));
		}
		role.setPvgs(pvgs);
		roleService.getRepository().save(role);
		return SUCCESS;
	}
	
	/**
	 * 角色授权
	 * @param roleId
	 * 			角色id
	 * @param pvgIds
	 * 			权限id集，每个权限用“,”分割
	 * */
	@RequestMapping(value ="roleAndPvgSave")
	public Message roleAndPvgSave(Role role,Long[] pvgIds){
		roleService.getRepository().save(role);
		List<Pvg> pvgs=new ArrayList<Pvg>();
		for (Long id:pvgIds){
			Pvg pvg = pvgService.getRepository().findOne(id);
			Pvg parent = pvg.getParent();
			//添加父权限
			if(parent!=null){
				if(!isExistPvg(pvgs,parent)){
					pvgs.add(parent);
				}
				for (Pvg p:pvg.getChildren()){
					if (!isExistPvg(pvgs,p)){
						pvgs.add(p);
					}
				}
			}
			pvgs.add(pvg);
		}
		role.setPvgs(pvgs);
		roleService.getRepository().save(role);
		return SUCCESS;
	}
	
	/**
	 * 角色授权查询
	 * */
	boolean isExistPvg(List<Pvg> pvgs,Pvg pvg){
		for(Pvg p : pvgs){
			if(pvg.getId() == p.getId()){
				return true;
			}
		}
		return false;
	}
}
