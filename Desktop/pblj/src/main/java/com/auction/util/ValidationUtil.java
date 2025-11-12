package com.auction.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean isPositive(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean isValidPhone(String phone) {
        return phone == null || phone.matches("^[0-9\\-\\+]{7,15}$");
    }

    public static boolean isEndAfterStart(LocalDateTime start, LocalDateTime end) {
        return start != null && end != null && end.isAfter(start);
    }
}


