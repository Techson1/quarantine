package com.beiqisoft.aoqun.entity.guide;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "g_joiningGuide")
public class JoiningGuide extends BaseEntity{

	private String JoiningType;
	
	private Long damId;
	
	private Date JoiningDate;
	
	private Long sireId;
	
	private Long breedingPlanId;
	
	private Long orgId;
	
	private Long groupId;
	
	private String BornType;
	
	private Long childBreedId;
	
	private String coreFlag;
	
	private Long paddock_id;
}
