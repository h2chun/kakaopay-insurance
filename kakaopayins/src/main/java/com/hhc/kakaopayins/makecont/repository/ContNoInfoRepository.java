package com.hhc.kakaopayins.makecont.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hhc.kakaopayins.global.entity.ContNoInfo;

public interface ContNoInfoRepository extends CrudRepository<ContNoInfo, Integer> {
	@Query(value="select max(idx_no) from cont_no_info", nativeQuery=true)
	int findMaxValue();
	
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE CONT_NO_INFO SET IDX_NO = ?", nativeQuery=true)
	void updateValue(int num);
	
	@Query(value="SELECT CONT_NO_SEQ.NEXTVAL FROM CONT_NO_INFO", nativeQuery=true)
	int findNextSeq();
	
}
