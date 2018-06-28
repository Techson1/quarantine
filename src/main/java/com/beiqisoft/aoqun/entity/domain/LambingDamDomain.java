package com.beiqisoft.aoqun.entity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.LambingDam;

@Data
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
public class LambingDamDomain {
	
	private BaseInfo dam;
	private BaseInfo sire;
	private BaseInfo breed;
	private String theSameFetus;
	private BaseInfo receptor;
	private LambingDam lambingDam;
}
