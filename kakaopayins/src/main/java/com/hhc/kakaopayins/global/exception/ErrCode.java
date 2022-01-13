package com.hhc.kakaopayins.global.exception;

import lombok.Getter;

@Getter
public enum ErrCode {
	
	E0001(404, "404 ERROR", "PAGE NOT FOUND."),
	E0002(500, "500 ERROR", "INTER SERVER ERROR."),
	E1000(1000, "E1000",  "상품/담보 코드가 정확하지 않습니다."),
	E1001(1001, "E1001",  "해당 상품에 계약기간을 확인바랍니다."),
	E1002(1002, "E1002",  "조회된 계약이 없습니다."),
	E1003(1003, "E1003",  "이미 기간이 만료된 계약입니다."),
	E1004(1004, "E1004",  "이미 계약이 종료된 건입니다."),
	E1005(1005, "E1005",  "담보가 중복 입력되었습니다."),
	E1006(1006, "E1006",  "담보정보를 입력 바랍니다."),
	E1007(1007, "E1007",  "상품정보를 입력 바랍니다."),
	E1008(1008, "E1008",  "계약기간을 입력 바랍니다."),
	E1009(1009, "E1009",  "계약번호를 입력 바랍니다."),
	E1010(1010, "E1010",  "계약기간은 숫자만 입력 가능합니다.");
	
	private final int status;
	private final String code;
	private final String errMsg;
    
	ErrCode(int status, String code, String errMsg){
		this.status = status;
        this.code = code;
        this.errMsg = errMsg;
    }

}