package com.sparta.baclub.board.validator;

import com.sparta.baclub.board.errormsg.ResultCode;
import com.sparta.baclub.board.exception.GlobalException;
import com.sparta.baclub.user.entity.User;

public class UserValidator {

    public static void validator(User user) {
        if (!isExistUser(user)) {
            throw new GlobalException(ResultCode.NOT_EXIST_USER);
        }
    }

    private static boolean isExistUser(User user) {
        return user != null;
    }
}