package com.beiqisoft.aoqun.entity.guide;

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
@Table(name = "sheepLevelGuide")
public class SheepLevelGuide extends BaseEntity{

	private Long baseId;
	private Long priceplanId;
	
	
}
