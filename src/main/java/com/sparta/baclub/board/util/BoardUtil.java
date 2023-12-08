package com.sparta.baclub.board.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class BoardUtil {

    public static String timestampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        TimeZone koreaTimeZone = TimeZone.getTimeZone("Asia/Seoul");
        timestamp.setTime(timestamp.getTime() + koreaTimeZone.getRawOffset());
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    }
}