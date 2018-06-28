package com.beiqisoft.aoqun.entity.guide;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "g_pregnancyGuide")
public class PregnancyGuide extends BaseEntity{

	@Size(max=20)
	private String result;
	
	private Long damId;
	
	private Date date;
	
	private Long orgId;
	
	private String BornType;
	
	private Long ProjectId;
	
	private Long joiningId;
}
