package com.lhmd.rechnerarchitektur.common;

public class IntUtils {
    public static Integer tryParse(String value) {
        return tryParse(value, 10);
    }

    public static Integer tryParse(String value, int radix) {
        if (StringUtils.isNullOrWhitespace(value)) {
            return null;
        }

        try {
            return Integer.parseInt(value, radix);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
