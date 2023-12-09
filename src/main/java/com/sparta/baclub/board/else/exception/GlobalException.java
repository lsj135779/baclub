package com.sparta.baclub.board.exception;

import com.sparta.baclub.board.errormsg.ResultCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private ResultCode resultCode;

    public GlobalException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }
}
