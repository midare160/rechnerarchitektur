package com.lhmd.rechnerarchitektur.Common;

public class IntTools {
    public static Integer tryParse(String value) {
        return tryParse(value, 10);
    }

    public static Integer tryParse(String value, int radix) {
        try {
            return Integer.parseInt(value, radix);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
