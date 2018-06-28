package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.User;
import com.beiqisoft.aoqun.repository.UserRepository;


public interface UserService extends BaseService<User, UserRepository>{
	/**
	 * 分页获取用户对象
	 * @param user 查询条件
	 * @return
	 */
	Page<User> find(User user);
	
	Page<User> find(User user, int pageNum);
	/**
	 * 用户登陆
	 * @param acc
	 * @param psw
	 * @return
	 */
	User login(String acc, String psw);
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	User login(User user);
	
}
