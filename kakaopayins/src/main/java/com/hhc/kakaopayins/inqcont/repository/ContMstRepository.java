package com.hhc.kakaopayins.inqcont.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hhc.kakaopayins.global.entity.ContMst;

public interface ContMstRepository extends CrudRepository<ContMst, String> {
	
	@Query(value="SELECT * FROM CONT_MST \r\n"
				+ "	WHERE CONT_NO = ?1\r\n"
				+ "	OR PRDT_INFO = ?1\r\n"
				+ "	OR INS_CVR = ?1\r\n"
				+ "	OR CONT_STAT = ?1", nativeQuery=true)
	List<ContMst> findVarCharType(String contInfo);
	
	@Query(value="SELECT * FROM CONT_MST \r\n"
				+ "WHERE TO_CHAR(INS_STDT, 'YYYYMMDD') = TO_CHAR(TO_DATE(?1, ?2), 'YYYYMMDD') \r\n"
				+ "OR TO_CHAR(INS_ENDDT , 'YYYYMMDD') = TO_CHAR(TO_DATE(?1, ?2), 'YYYYMMDD') \r\n"
				, nativeQuery=true)
	List<ContMst> findDateType(String contInfo, String fromat);
	
	@Query(value="SELECT * FROM CONT_MST \r\n"
				+"WHERE CONT_PRD = TRUNC(?1)	\r\n"
				+"OR TOT_INS_PREM = ?1	\r\n"
			, nativeQuery=true)
	List<ContMst> findNumberType(BigDecimal contInfo);
}
