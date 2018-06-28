package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter; 
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.util.MyUtils;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_rankTest")
public class RankTest extends BaseEntity{
	
	/**品种*/
	@ManyToOne @JoinColumn(name="breed_id")
	private Breed breed;
	/**性别*/
	@Size(max=2)
	private String sex;
	/**名称*/
	@Size(max=36)
	private String name;
	/**级别*/
	@Size(max=20)
	private String rank;
	/**价格*/
	private String price;
	
	public boolean isRank(String rank){
		return MyUtils.com(this.rank, rank);
	}
	
	public boolean isRank(){
		if ("0".equals(rank)){
			this.rank=null;
			return true;
		}
		return false;
	}
	
	public double priceShift(){
		try{
			return Double.parseDouble(this.price);
		}catch(Exception e){
			return 0.00;
		}
	}
	
	public RankTest(Long id){
		this.id=id;
	}

}
