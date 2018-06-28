package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.User;

public interface UserRepository  extends BaseRepository<User>{
	
	/**
	 * 根据账号查询User
	 * @param userName
	 * 		账号
	 * @return User
	 * */
	User findByUserName(String userName);
	
	/**
	 * 根据账号密码查询User
	 * @param acc
	 * 		用户名
	 * @param psw
	 * 		密码
	 * @return User
	 * */
	User findByUserNameAndPassWord(String acc,String psw);
	
	/**
	 * 根据手机号查询User
	 * @param phone
	 * 		手机号
	 * @return User
	 * */
	User findByPhone(String phone);
}
