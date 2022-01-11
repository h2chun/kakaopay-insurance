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
        name="CONT_NO_SEQ_GEN", //시퀀스 제너레이터 이름
        sequenceName="CONT_NO_SEQ", //시퀀스 이름
        initialValue=3, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
        )

public class ContNoInfo {
	@Id
	@GeneratedValue(
            strategy=GenerationType.SEQUENCE, //사용할 전략을 시퀀스로  선택
            generator="CONT_NO_SEQ_GEN" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정
            )
	private int idxNo;				//계약번호
}
