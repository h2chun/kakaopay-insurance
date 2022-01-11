package com.hhc.kakaopayins.global.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Data
@Entity	
@SequenceGenerator(
        name="CONT_NO_SEQ_GEN", 
        sequenceName="CONT_NO_SEQ",
        initialValue=3, //초기 데이터 2건을 계약테이블에 insert 시키기 때문에 3부터 시작함.
        allocationSize=1 
        )

public class ContNoInfo {
	@Id
	@GeneratedValue(
            strategy=GenerationType.SEQUENCE,
            generator="CONT_NO_SEQ_GEN" 
            )
	private int idxNo;				//계약번호
}
