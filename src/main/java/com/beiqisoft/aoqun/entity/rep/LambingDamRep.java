package com.beiqisoft.aoqun.entity.rep;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.entity.Breed;

@Data
@Getter @Setter @NoArgsConstructor @ToString
public class LambingDamRep {
	
	/**品种*/
	private Breed breed;
	/**产羔胎次数*/
	private Long parityNum;
	/**产羔总数*/
	private Long bornTimes;
	/**活羔数*/
	private Long aliveCount;
	/**畸形数*/
	private Long badCount;
	/**死胎数*/
	private Long bornDateAssistEnd;
	
	
	public LambingDamRep(Breed breed,Long parityNum,Long bornTimes,Long aliveCount,Long badCount,Long bornDateAssistEnd){
		this.breed=breed;
		this.parityNum=parityNum;
		this.bornTimes=bornTimes;
		this.aliveCount=aliveCount;
		this.badCount=badCount;
		this.bornDateAssistEnd=bornDateAssistEnd;
	}
	
	public LambingDamRep(Breed breed){
		
	}
	
	public LambingDamRep(Long count){
		
	}
}
