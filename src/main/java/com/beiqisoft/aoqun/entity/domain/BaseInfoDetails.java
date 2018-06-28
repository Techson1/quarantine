package com.beiqisoft.aoqun.entity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.beiqisoft.aoqun.entity.BaseInfo;

@Data
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
public class BaseInfoDetails {
	
	/**羊只*/
	private BaseInfo base;
	/**胚移受体次数*/
	private Integer embryoReceptorNumber;
	/**移植胚胎数*/
	private Integer transplantedEmbryonicNumber;
	/**产活羔数*/
	private Integer lambNumber;
}
