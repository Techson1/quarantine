 package com.beiqisoft.aoqun.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.DeathDisposalReason;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.User;
import com.beiqisoft.aoqun.service.UserService;

/**
 * 用户访问控制类
 * @author 王栋
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "user")
public class UserController extends BaseController<User,UserService> {
	
	@RequestMapping(value ="list")
    public Page<User> list(User user) throws InterruptedException{
		return userService.find(user);
    }
	
	/**
	 * 修改密码
	 * */
	@RequestMapping(value="resetPassWord")
	public Message resetPassWord(Long id,String passWord,String editPassWord,String passWorded){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		User user = userService.getRepository().findOne(id);
		if (!passWord.equals(editPassWord)){
			return GlobalConfig.setAbnormal("两次密码不一致,请重新输入");
		}
		if (!encoder.matches(passWorded,user.getPassWord())){
			return GlobalConfig.setAbnormal("旧密码与新密码不一致,请重新输入");
		}
		user.setPassWord(encoder.encode(passWord.trim()));
		userService.getRepository().save(user);
		return SUCCESS;
	}
	
	/**
	 * 修改密码
	 * */
	@RequestMapping(value="editPassWord")
	public Message editPassWord(Long id){
		User user = userService.getRepository().findOne(id);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassWord(encoder.encode(user.getUserName().trim()));
		userService.getRepository().save(user);
		return SUCCESS;
	}
	
	/**
	 * app初始化数据
	 * */
	@RequestMapping(value="userInit")
	public Map<String,Object> userInit(){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("breed", breedService.getRepository().findAll());
		map.put("rankTest", rankTestService.getRepository().findAll());
		map.put("deathDisposalReason",deathAnalysis(
				(List<DeathDisposalReason>)deathDisposalReasonService.getRepository().findAll()));
		map.put("fakeTypeDam", fakeTypeDamService.getRepository().findAll());
		map.put("fakeTypeSire", fakeTypeSireService.getRepository().findAll());
		map.put("customer",customerService.getRepository().findAll());
		map.put("paddock", paddockService.livestockTest("",null));
		return map;
	}
	
	private List<DeathDisposalReason> deathAnalysis(List<DeathDisposalReason> deathList){
		for (DeathDisposalReason d:deathList){
			d.setParentId(d.getParent()==null?null:d.getParent().getId());
		}
		return deathList;
	}
	
	/**
	 * 用户修改
	 * */
	@RequestMapping(value ="edit")
	public Message edit(User user){
		User usered= userService.getRepository().findOne(user.getId());
		usered.setRole(user.getRole());
		usered.setPhone(user.getPhone());
		userService.getRepository().save(usered);
		return SUCCESS;
	}
	
	/**
	 * 账号校验
	 * 		账号不能重复,如果重复返回101,否则100
	 * @param userName
	 * 		账号
	 * */
	@RequestMapping(value ="userNameVerify")
	public boolean userNameVerify(String userName,Long id){
		User user= userService.getRepository().findByUserName(userName);
		if (id == null && user!=null) return false;
		if (user!=null && !user.getId().equals(id)) 
			return false;
		return true;
	}
	
	@RequestMapping(value ="exist")
	public Boolean exist(String userName){
		if (userService.getRepository().findByUserName(userName)!=null){
			return false;
		}
		return true;
	}
	
	/**
	 * 手机号校验
	 * */
	@RequestMapping(value ="phoneVerify")
	public boolean phoneVerify(String phone){
		return userService.getRepository().findByPhone(phone)==null;
	}
	
	@RequestMapping(value ="test")
	public Object test(){
		return currentUser();
	}
	@RequestMapping("cancel/{id}")
	public Message cancel(@PathVariable Long id) {
		User user = userService.getRepository().findOne(id);
		user.setState(0);
		getRepository().save(user);
		return SUCCESS;
	}
}
	
	
	
	