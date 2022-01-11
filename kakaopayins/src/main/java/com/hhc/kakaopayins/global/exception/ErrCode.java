package com.hhc.kakaopayins.global.exception;

import lombok.Getter;

@Getter
public enum ErrCode {
	
	E0001(404, "404 ERROR", "PAGE NOT FOUND."),
	E0002(500, "500 ERROR", "INTER SERVER ERROR."),
	E1000(101, "E1000",  "��ǰ/�㺸 �ڵ尡 ��Ȯ���� �ʽ��ϴ�."),
	E1001(102, "E1001",  "�ش� ��ǰ�� ���Ⱓ�� Ȯ�ιٶ��ϴ�."),
	E1002(103, "E1002",  "��ȸ�� ����� �����ϴ�."),
	E1003(104, "E1003",  "�̹� ���Ⱓ�� ����� ����Դϴ�.");
	
	private final int status;
	private final String code;
	private final String errMsg;
    
	ErrCode(int status, String code, String errMsg){
		this.status = status;
        this.code = code;
        this.errMsg = errMsg;
    }

}