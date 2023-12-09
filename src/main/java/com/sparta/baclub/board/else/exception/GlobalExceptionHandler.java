package com.sparta.baclub.board.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({GlobalException.class})
    public RestResponse handleCustomException(GlobalException e) {
        return RestResponse.error(e.getResultCode());
    }
}
