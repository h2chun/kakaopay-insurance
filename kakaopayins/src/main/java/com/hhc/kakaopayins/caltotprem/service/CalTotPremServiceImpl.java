package com.hhc.kakaopayins.caltotprem.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hhc.kakaopayins.caltotprem.repository.CalTotPremRepository;
import com.hhc.kakaopayins.global.entity.CvrInfo;
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
	public BigDecimal calTotPrem(String prdtInfo, String insCvr, int contPrd) {
		
		ValidationUtil.chkDupCvrInfo(insCvr);	//중복입력여부 체크
		
		List<CvrInfo> cvrInfoList = cvrInfoRepository.findPrdtInfo(prdtInfo);
		ValidationUtil.chkContPrd(cvrInfoList, contPrd);
		
		BigDecimal rtn = new BigDecimal("0");
		rtn = CalculationUtil.calTotInsPrem2(prdtInfo, insCvr, contPrd, calTotPremRepository);
		return rtn;
	}

}
