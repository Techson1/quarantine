package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.EmbryoGrading;

public interface EmbryoGradingRepository extends BaseRepository<EmbryoGrading>{
	
	EmbryoGrading findByQualityGradeAndGeneGradeAndBreed_id(String qualityGrade,String geneGrade,Long breedId);
}
