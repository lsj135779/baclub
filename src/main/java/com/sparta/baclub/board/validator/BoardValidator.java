package com.sparta.baclub.board.validator;

import com.sparta.baclub.board.entity.BoardEntity;
import com.sparta.baclub.board.errormsg.ResultCode;
import com.sparta.baclub.board.exception.GlobalException;

public class BoardValidator {

    public static void validate(BoardEntity board) {
        if (!isExistBoard(board)) {
            throw new GlobalException(ResultCode.NOT_EXIST_BOARD);
        }
    }


    private static boolean isExistBoard(BoardEntity board) {
        return board != null;
    }
}