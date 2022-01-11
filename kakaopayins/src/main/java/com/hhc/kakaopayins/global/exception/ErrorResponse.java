package com.hhc.kakaopayins.global.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	private int status;
    private String message;
    private String code;

    public ErrorResponse(ErrCode errCode){
    	this.status = errCode.getStatus();
        this.message = errCode.getErrMsg();
        this.code = errCode.getCode();
    }
}
