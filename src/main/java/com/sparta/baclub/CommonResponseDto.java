package com.sparta.baclub;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CommonResponseDto {
    private String msg;
    private Integer statusCode;

    public CommonResponseDto(String message, int value) {
        this.msg = message;
        this.statusCode = value;
    }
}
