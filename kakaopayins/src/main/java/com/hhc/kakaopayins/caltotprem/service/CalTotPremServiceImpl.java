package com.hhc.kakaopayins.caltotprem.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.hhc.kakaopayins.caltotprem.repository.CalTotPremRepository;
import com.hhc.kakaopayins.global.util.CalculationUtil;

@Service
public class CalTotPremServiceImpl implements CalTotPremService {
	private CalTotPremRepository calTotPremRepository;

	public CalTotPremServiceImpl(CalTotPremRepository calTotPremRepository) {
		super();
		this.calTotPremRepository = calTotPremRepository;
	}

	@Override
	public BigDecimal calTotPrem(String prdtInfo, String insCvr, int contPrd) {
		BigDecimal rtn = new BigDecimal("0");
		rtn = CalculationUtil.calTotInsPrem2(prdtInfo, insCvr, contPrd, calTotPremRepository);
		return rtn;
	}

}
