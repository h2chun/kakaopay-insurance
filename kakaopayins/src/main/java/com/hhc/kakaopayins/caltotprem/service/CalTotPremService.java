package com.hhc.kakaopayins.caltotprem.service;

import java.math.BigDecimal;

public interface CalTotPremService {
	BigDecimal calTotPrem(String prdtInfo, String insCvr, int contPrd);
}
