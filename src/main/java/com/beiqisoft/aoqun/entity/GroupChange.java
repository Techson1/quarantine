package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_groupChange")
public class GroupChange extends BaseEntity{
	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**原定级*/
	@ManyToOne @JoinColumn(name="from_rank_id")
	private RankTest formRank;
	/**新定级*/
	@ManyToOne @JoinColumn(name="to_rank_id")
	private RankTest toRank;
	/**原圈舍*/
	@ManyToOne @JoinColumn(name="from_paddock_id")
	private Paddock formPaddock;
	/**新定级*/
	@ManyToOne @JoinColumn(name="to_paddock_id")
	private Paddock toPaddock;
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	
	/**
	 * 修改羊只并保存转入饲舍
	 */
	public GroupChange setBaseReturnThis(BaseInfo base){
		this.base=base;
		this.formRank=base.getRank();
		this.formPaddock=base.getPaddock();
		if (this.toPaddock!=null && toPaddock.getId()==null){
			this.toPaddock=null;
		}
		return this;
	}
	
	public GroupChange setToRankReturnThis(RankTest toRank){
		this.toRank=toRank;
		return this;
	}
}
