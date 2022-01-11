package com.hhc.kakaopayins.makecont.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hhc.kakaopayins.global.entity.CvrInfo;

public interface CvrInfoRepository extends CrudRepository<CvrInfo, String> {
	@Query(value="SELECT * FROM CVR_INFO WHERE CVR_CD = ?1 AND PRDT_CD = ?2", nativeQuery=true)
	CvrInfo findCvrInfo(String str1, String str2);
	
	@Query(value="SELECT * FROM CVR_INFO WHERE PRDT_CD = ?1", nativeQuery=true)
	List<CvrInfo> findPrdtInfo(String str1);
}
