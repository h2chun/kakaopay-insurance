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
        name="CONT_NO_SEQ_GEN", //������ ���ʷ����� �̸�
        sequenceName="CONT_NO_SEQ", //������ �̸�
        initialValue=3, //���۰�
        allocationSize=1 //�޸𸮸� ���� �Ҵ��� ���� ������
        )

public class ContNoInfo {
	@Id
	@GeneratedValue(
            strategy=GenerationType.SEQUENCE, //����� ������ ��������  ����
            generator="CONT_NO_SEQ_GEN" //�ĺ��� �����⸦ �����س���  USER_SEQ_GEN���� ����
            )
	private int idxNo;				//����ȣ
}
