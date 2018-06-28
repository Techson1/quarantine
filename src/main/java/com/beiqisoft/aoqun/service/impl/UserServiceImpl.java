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

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.entity.User;
import com.beiqisoft.aoqun.repository.UserRepository;
import com.beiqisoft.aoqun.service.UserService;


@Service
public class UserServiceImpl extends BaseServiceIml<User,UserRepository> implements UserService{

	@Autowired
	public UserRepository userRepository;
	
	public Page<User> find(final User user) {
		return userRepository.findAll(new Specification<User>() {
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(user,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(user.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<User> find(User user, int size) {
		return userRepository.findAll(new Specification<User>() {
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public UserRepository getRepository() {
		return userRepository;
	}

	@Override
	public User login(String acc, String psw) {
		return userRepository.findByUserNameAndPassWord(acc,psw);
	}

	@Override
	public User login(User user) {
		return userRepository.findByUserNameAndPassWord(user.getUserName(),user.getPassWord());
	}

	

}
