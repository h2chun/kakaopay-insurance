package com.hhc.kakaopayins.global.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity	
public class PrdtInfo {
	@Id
	private String prdtCd;
	private String prdtNm;
	private int minContPrd;
	private int maxContPrd;
}
