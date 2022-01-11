package com.hhc.kakaopayins.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class KakaoExcepionHandler {
	@ExceptionHandler(KakaoException.class)
    public ResponseEntity<ErrorResponse> handleKakaoException(KakaoException ex){
        log.error("handleKakaoException",ex);
        
        ErrorResponse response = new ErrorResponse(ex.getErrCode());
        
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        log.error("handleException",ex);
        ErrorResponse response = new ErrorResponse(ErrCode.E0002);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
