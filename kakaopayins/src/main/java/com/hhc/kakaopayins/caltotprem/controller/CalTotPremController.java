package com.hhc.kakaopayins.caltotprem.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhc.kakaopayins.caltotprem.service.CalTotPremService;

@RestController
public class CalTotPremController {
	
	private CalTotPremService service;
	
	public CalTotPremController(CalTotPremService service) {
		super();
		this.service = service;
	}

	@GetMapping("/cal")
	public BigDecimal dlngContMst(String prdtInfo, String insCvr, int contPrd) throws Exception {
		
		BigDecimal rtn = service.calTotPrem(prdtInfo, insCvr, contPrd);
		return rtn;
	}
}
