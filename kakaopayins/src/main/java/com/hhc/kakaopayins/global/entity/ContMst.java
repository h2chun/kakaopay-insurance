package com.hhc.kakaopayins.global.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ContMst {
	@Id
	private String contNo;				//계약번호
	private String prdtInfo;		//상품정보
	private String insCvr;			//가입담보
	private int contPrd;			//계약기간
	//@Column(name = "DATE_FIELD")
	private Date insStdt;	//보험시작일
	//@Column(name = "DATE_FIELD")
	private Date insEnddt;	//보험종료일
	private BigDecimal totInsPrem;	//총보험료
	private String contStat;		//계역상태
}