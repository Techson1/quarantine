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

/**
 * 胚胎定价实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_embryoGrading")
public class EmbryoGrading extends BaseEntity{
	/**品种*/
	@ManyToOne @JoinColumn(name = "breed_id")
	private Breed breed;
	/**质量等级*/
	@Size(max=20)
	private String qualityGrade;
	/**基因等级*/
	@Size(max=20)
	private String geneGrade;
	/**销售定价*/
	@Size(max=20)
	private String salesPricing;
}
