package com.hhc.kakaopayins.inqcont.service;

import java.util.List;

import com.hhc.kakaopayins.global.entity.ContMst;

public interface InqContService {
	List<ContMst> getContMst(String contInfo);
}
