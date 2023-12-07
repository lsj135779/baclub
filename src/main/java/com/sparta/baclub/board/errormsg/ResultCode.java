package com.sparta.baclub.board.meta;

public enum ResultCode {
    SUCCESS(0, "정상 처리 되었습니다"),
    SYSTEM_ERROR(1000, "알 수 없는 애러가 발생했습니다."),
    NOT_EXIST_BOARD(2002, "게시글이 존재하지 않거나 작성자가 아닙니다."),
    NOT_EXIST_COMMENT(2003, "댓글이 존재하지 않거나 작성자가 아닙니다."),
    NO_MATCHES_PASSWORD(5000, "패스워드가 일치하지 않습니다.");

    private Integer code;
    private String message;

    ResultCode(Integer code, String errorMessage) {
        this.code = code;
        this.message = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
