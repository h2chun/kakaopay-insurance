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
	private String contNo;				//����ȣ
	private String prdtInfo;		//��ǰ����
	private String insCvr;			//���Դ㺸
	private int contPrd;			//���Ⱓ
	//@Column(name = "DATE_FIELD")
	private Date insStdt;	//���������
	//@Column(name = "DATE_FIELD")
	private Date insEnddt;	//����������
	private BigDecimal totInsPrem;	//�Ѻ����
	private String contStat;		//�迪����
}