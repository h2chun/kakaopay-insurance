package com.hhc.kakaopayins.modcont.service;

import com.hhc.kakaopayins.global.entity.ContMst;

public interface ModContService {
	ContMst modInsCvr(String contNo, String insCvr);
	ContMst modContPrd(String contNo, String contPrd) throws Exception;
	ContMst modContStat(String contNo, String contStat);
}
