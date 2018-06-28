package com.beiqisoft.aoqun.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.config.SystemM;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 权限实体类
 * @author 王栋
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity
@Table(name = "t_pvg")
public class Pvg extends BaseEntity{
	public static final int TYPE_PARENT = 0;//一级导航 
	public static final int TYPE_NAV = 1;//左边菜单 
	public static final int TYPE_OPERA = 2;//操作按钮
	public static final int TYPE_APP = 3;//APP按钮
	
	@Size(max=50)
	private String url;
	@Size(max=20)
	private String name;
	@Size(max=20)
	private String icon;
	private Integer type;
	
	/**是否拥有权限*/
	@Transient
	private String isPvg=SystemM.PUBLIC_FALSE;
	
	@ManyToOne @JoinColumn(name = "parent_id") @JsonIgnore
	private Pvg parent;
	@OneToMany(mappedBy = "parent",cascade={CascadeType.PERSIST}) 
	private List<Pvg> children;
	
	@ManyToMany(mappedBy = "pvgs") @JsonIgnore
	private Set<User> users;
	
	@ManyToMany(mappedBy = "pvgs") @JsonIgnore
	private Set<Role> roles;
	
	public Pvg(Long id) {
		super();
		this.id=id;
	}
	
	public Pvg(String url, String name, String icon,int type, Pvg parent) {
		super();
		this.url = url;
		this.name = name;
		this.icon = icon;
		this.parent = parent;
		this.type = type;
	}
	
	public Pvg(String url, String name, String icon) {
		super();
		this.url = url;
		this.name = name;
		this.icon = icon;
		this.type = Pvg.TYPE_PARENT;
	}
	
	public Pvg(String url, String name, String icon,Pvg parent) {
		super();
		this.url = url;
		this.name = name;
		this.icon = icon;
		this.parent = parent;
		this.type = Pvg.TYPE_NAV;
	}
	
	public Pvg(String url, String name) {
		super();
		this.url = url;
		this.name = name;
		this.parent = null;
		this.type = Pvg.TYPE_APP;
	}

	public Pvg setIsPvgReturnThis(String isPvg) {
		this.isPvg=isPvg;
		return this;
	}
	
	public void setIsPvg(String isPvg){
		this.isPvg=isPvg;
	}
}
