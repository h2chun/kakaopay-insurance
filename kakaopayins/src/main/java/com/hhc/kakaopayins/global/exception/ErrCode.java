package com.hhc.kakaopayins.global.exception;

import lombok.Getter;

@Getter
public enum ErrCode {
	
	E0001(404, "404 ERROR", "PAGE NOT FOUND."),
	E0002(500, "500 ERROR", "INTER SERVER ERROR."),
	E1000(101, "E1000",  "상품/담보 코드가 정확하지 않습니다."),
	E1001(102, "E1001",  "해당 상품에 계약기간을 확인바랍니다."),
	E1002(103, "E1002",  "조회된 계약이 없습니다."),
	E1003(104, "E1003",  "이미 계약기간이 만료된 계약입니다.");
	
	private final int status;
	private final String code;
	private final String errMsg;
    
	ErrCode(int status, String code, String errMsg){
		this.status = status;
        this.code = code;
        this.errMsg = errMsg;
    }

}