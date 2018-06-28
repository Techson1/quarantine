package com.beiqisoft.aoqun.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

import com.beiqisoft.aoqun.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 角色实体类
 * @author 王栋
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_role")
public class Role extends BaseEntity{
	
	public static final int TYPE_WEB=1;
	public static final int TYPE_APP=2;
	
	@Size(max=36)
	private String name;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "t_role_pvg", 
    	joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }, 
    	inverseJoinColumns = { @JoinColumn(name = "pvg_id", referencedColumnName = "id") })
	private List<Pvg> pvgs;
	
	private Integer type;
	
	/**用户*/
	@ManyToOne @JoinColumn(name="user_id")
	private User user;
	
	public List<Long> getPvgIds(){
		List<Long> plist = new LinkedList<Long>();
		for(Pvg pvg : getPvgs()){
			plist.add(pvg.getId());
		}
		return plist;
	}
	
	public Role(String roleName,int type){
		this.name=roleName;
	}
}
