package com.hhc.kakaopayins.caltotprem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hhc.kakaopayins.global.entity.CvrInfo;

public interface CalTotPremRepository extends CrudRepository<CvrInfo, String> {
	@Query(value="SELECT * FROM CVR_INFO WHERE CVR_CD = ?1 AND PRDT_CD = ?2", nativeQuery=true)
	CvrInfo findCvrInfo(String str1, String str2);
}
