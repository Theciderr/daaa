package com.auction.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateTimeUtil {

    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeUtil() {
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static String formatDisplay(LocalDateTime time) {
        if (time == null) {
            return "";
        }
        return DISPLAY_FORMAT.format(time);
    }

    public static long secondsUntil(LocalDateTime time) {
        if (time == null) {
            return 0;
        }
        return Duration.between(LocalDateTime.now(), time).getSeconds();
    }
}


