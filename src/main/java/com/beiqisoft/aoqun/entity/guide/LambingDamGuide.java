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
@Table(name = "g_lambingGuide")
public class LambingDamGuide extends BaseEntity{	
	private Long damId;
	private Date bornDate;
	
	private String bornConunt1;
	private String AliveCount1;
	private String deadCount1;
	private String badCount1;
	
	private String bornConunt2;
	private String AliveCount2;
	private String deadCount2;
	private String badCount2;
	
	private Long orgId;
	private String motherhood;
	private String masitis;
	private String breas;
	private String weaningAliveCount;
	private String weaningAliveReta;
	private String flag;
	private String easyFlag;
	@Size(max=255)
	private String error;
	
	private Long joining;
}
