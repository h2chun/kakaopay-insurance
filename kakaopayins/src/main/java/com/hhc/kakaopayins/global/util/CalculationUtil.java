package com.hhc.kakaopayins.global.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.hhc.kakaopayins.caltotprem.repository.CalTotPremRepository;
import com.hhc.kakaopayins.global.entity.CvrInfo;
import com.hhc.kakaopayins.global.exception.ErrCode;
import com.hhc.kakaopayins.global.exception.KakaoException;
import com.hhc.kakaopayins.makecont.repository.CvrInfoRepository;

public class CalculationUtil {
	
	public static BigDecimal calTotInsPrem(String prdtCd, String insCvr, int contPrd, CvrInfoRepository cvrInfoRepository){
		
		BigDecimal totInsPrem = new BigDecimal("0");
		String cvrArr[] = insCvr.split(",");
		for(String cvr : cvrArr) {
			
			CvrInfo cvrInfo = cvrInfoRepository.findCvrInfo(cvr, prdtCd);
			
			if(cvrInfo == null) {
				throw new KakaoException(ErrCode.E1000.getErrMsg(), ErrCode.E1000);
			}
			
			BigDecimal tPrem = cvrInfo.getInsuredAmt().divide(cvrInfo.getStndAmt(), 2, RoundingMode.FLOOR);
			/*
			int dotIdx = tPrem.indexOf(".");
			if(dotIdx >= 0) {
				tPrem = tPrem.substring(0, dotIdx+3);
			}
			*/
			totInsPrem = totInsPrem.add(tPrem);
		}
		totInsPrem = totInsPrem.multiply(new BigDecimal(contPrd));
		
		return totInsPrem;
	}
	
	public static BigDecimal calTotInsPrem2(String prdtCd, String insCvr, int contPrd, CalTotPremRepository calTotPremRepository) {
		
		BigDecimal totInsPrem = new BigDecimal("0");
		String cvrArr[] = insCvr.split(",");
		for(String cvr : cvrArr) {
			
			CvrInfo cvrInfo = calTotPremRepository.findCvrInfo(cvr, prdtCd);
			
			if(cvrInfo == null) {
				throw new KakaoException(ErrCode.E1000.getErrMsg(), ErrCode.E1000);
			}
			
			BigDecimal tPrem = cvrInfo.getInsuredAmt().divide(cvrInfo.getStndAmt(), 2, RoundingMode.FLOOR);
			/*
			int dotIdx = tPrem.indexOf(".");
			if(dotIdx >= 0) {
				tPrem = tPrem.substring(0, dotIdx+3);
			}
			*/
			totInsPrem = totInsPrem.add(tPrem);
		}
		totInsPrem = totInsPrem.multiply(new BigDecimal(contPrd));
		
		return totInsPrem;
	}
}