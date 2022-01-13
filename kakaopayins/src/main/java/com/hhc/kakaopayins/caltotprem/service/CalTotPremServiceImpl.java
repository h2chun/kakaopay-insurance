package com.hhc.kakaopayins.caltotprem.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hhc.kakaopayins.caltotprem.repository.CalTotPremRepository;
import com.hhc.kakaopayins.global.entity.CvrInfo;
import com.hhc.kakaopayins.global.exception.ErrCode;
import com.hhc.kakaopayins.global.exception.KakaoException;
import com.hhc.kakaopayins.global.util.CalculationUtil;
import com.hhc.kakaopayins.global.util.ValidationUtil;
import com.hhc.kakaopayins.makecont.repository.CvrInfoRepository;

@Service
public class CalTotPremServiceImpl implements CalTotPremService {
	private CalTotPremRepository calTotPremRepository;
	private CvrInfoRepository cvrInfoRepository;

	public CalTotPremServiceImpl(CalTotPremRepository calTotPremRepository, CvrInfoRepository cvrInfoRepository) {
		super();
		this.calTotPremRepository = calTotPremRepository;
		this.cvrInfoRepository = cvrInfoRepository;
	}

	@Override
	public BigDecimal calTotPrem(String prdtInfo, String insCvr, String contPrd) {
		
		ValidationUtil.nullChkByPrdtInfo(prdtInfo);	//상품정보 null체크
		ValidationUtil.nullChkByCvrInfo(insCvr);		//담보정보 null체크
		Optional.ofNullable(contPrd).orElseThrow(() -> new KakaoException(ErrCode.E1008.getErrMsg(), ErrCode.E1008) );	//계약기간 null체크;		//계약기간 null체크
		
		ValidationUtil.chkContPrdNumeric(contPrd);	//계약기간이 숫자인지 체크
		
		ValidationUtil.chkDupCvrInfo(insCvr);	//중복입력여부 체크
		
		List<CvrInfo> cvrInfoList = cvrInfoRepository.findPrdtInfo(prdtInfo);
		ValidationUtil.chkContPrd(cvrInfoList, Integer.parseInt(contPrd));
		
		BigDecimal rtn = new BigDecimal("0");
		rtn = CalculationUtil.calTotInsPrem2(prdtInfo, insCvr, Integer.parseInt(contPrd), calTotPremRepository);
		return rtn;
	}

}
