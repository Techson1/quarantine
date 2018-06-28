package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Role;
import com.beiqisoft.aoqun.entity.User;
import com.beiqisoft.aoqun.service.OrganizationService;

/**
 * 分厂访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "organization")
public class OrganizationController extends BaseController<Organization,OrganizationService> {
	
	@RequestMapping(value ="list")
    public Page<Organization> list(Organization organization) throws InterruptedException{
		return organizationService.find(organization);
	}
	
	/**
	 * 重置密码
	 * */
	@RequestMapping(value="resetPassWord")
	public Message resetPassWord(Long id,String passWord){
		Organization org = organizationService.getRepository().findOne(id);
		User user = userService.getRepository().findByUserName(org.getUserName());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//重置密码
		user.setPassWord(encoder.encode(user.getUserName()));
		userService.getRepository().save(user);
		return SUCCESS;
	}
	
	/**
	 * 分场添加校验
	 * 		如果"分厂名称","英文代码","电话"不唯一则返回失败,及失败原因
	 * @param orgName
	 * 			分厂名称
	 * @param code
	 * 			英文代码
	 * @param phone
	 * 			电话
	 * */
	@RequestMapping(value ="addVerify")
	public boolean addVerify(Organization organization){
		if (!organizationService.getRepository().findByOrgName(organization.getOrgName()).isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * 分场全称校验
	 * */
	@RequestMapping(value ="orgNameVerify")
	public boolean orgNameVerify(String orgName,Long id){
		Organization org= organizationService.getRepository().findByOrgNameV(orgName);
		if (id == null && org!=null) return false;
		if (org!=null && !org.getId().equals(id)) 
			return false;
		return true;
	}
	
	/**
	 * 分厂删除
	 * 		分厂只有是新建的并且没有和任何数据关联的才可以删除,否则不能删除
	 * @param id
	 * */
	@RequestMapping(value ="delete")
	public Message delete(Long id){
		//TODO:CZX:删除功能尚未实现
		return null;
	}
	
	@RequestMapping(value="add")
	public Message add(Organization organization,String userName,String cName,String passWord,String type){
		User user =new User();
		user.setUserName(userName);
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassWord(encoder.encode(passWord));
		user.setCname(cName);
		List<Role> role= roleService.getRepository().findByName("虚拟");
		if (role.isEmpty()){
			return GlobalConfig.setAbnormal("虚拟角色没有创建,请联系管理员");
		}
		if (userService.getRepository().findByUserName(userName)!=null){
			return GlobalConfig.setAbnormal("账号已存在请更换");
		}
		organization.setUserName(userName);
		organization.setCName(cName);
		organizationService.getRepository().save(organization);
		user.setRole(role.get(0));
		user.setOrganization(organization);
		user.setType(type);
		userService.getRepository().save(user);
		return SUCCESS;
	}
}