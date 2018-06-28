package com.beiqisoft.aoqun.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户实体类
 * @author 王栋
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_user")
public class User extends BaseEntity{
	/**账号*/
	@Size(max=20)
	private String userName;
	/**密码*/
	@JsonIgnore
	private String passWord;
	/**电子邮件*/
	@Size(max=20)
	private String email;
	/**用户名*/
	@Size(max=36)
	private String cname;
	/**地址*/
	@Size(max=20)
	private String address;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastPasswordResetDate;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "t_user_pvg", 
    	joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, 
    	inverseJoinColumns = { @JoinColumn(name = "pvg_id", referencedColumnName = "id") })
	private Set<Pvg> pvgs;
	/**角色*/
	@ManyToOne @JoinColumn(name = "role_id")
	private Role role;
	/**手机号*/
	@Size(max=20)
	private String phone;
	/**账号范围*/
	@Size(max=20)
	private String accountScope;
	/**权限类型*/
	@Size(max=20)
	private String pvgType;
	/**账号类型*/
	@Size(max=20)
	private String accountType;
	/**
	 * 类型
	 *  1:超级管理员
	 *  2:集团
	 *  3:分厂
	 *  4:员工
	 * */
	@Size(max=4)
	private String type;
	
	private Integer state;
	@ManyToOne  
	@JoinColumn(name = "organization_id")
	private Organization organization;
}
