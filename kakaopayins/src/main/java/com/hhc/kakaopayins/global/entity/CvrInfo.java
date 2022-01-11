package com.hhc.kakaopayins.global.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity	
public class CvrInfo {
	
	@Id
	private String cvrCd;
	private String cvrNm;
	private BigDecimal insuredAmt;
	private BigDecimal stndAmt;
	
	@ManyToOne
    @JoinColumn(name = "prdt_cd")
    private PrdtInfo prdtInfo;
    
}
