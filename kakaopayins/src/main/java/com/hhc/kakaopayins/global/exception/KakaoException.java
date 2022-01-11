package com.hhc.kakaopayins.global.exception;

import lombok.Getter;

@Getter
public class KakaoException extends RuntimeException{
	/**
	 * ����ó�� ���� ��
	 */
	private static final long serialVersionUID = 1L;
	
	private ErrCode errCode;

    public KakaoException(String message, ErrCode errCode) {
        super(message);
        this.errCode = errCode;
    }
}
