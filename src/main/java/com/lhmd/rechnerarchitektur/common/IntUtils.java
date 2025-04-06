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

    /**
     * Extracts a specified range of bits from an integer.
     *
     * <p>This method extracts the bits from position {@code from} to {@code to} (inclusive)
     * in the given {@code value}. Bit positions are zero-indexed, with 0 being the least significant bit.</p>
     *
     * @param value the integer value to extract bits from
     * @param from  the starting bit position (inclusive), where 0 is the least significant bit
     * @param to    the ending bit position (inclusive)
     * @return the extracted bits, aligned to the least significant bits of the result
     * @throws IllegalArgumentException if {@code from < 0}, {@code to > 31}, or {@code from > to}
     */
    public static int bitRange(int value, int from, int to) {
        if (from < 0 || to > 31 || from > to) {
            throw new IllegalArgumentException("Invalid bit range");
        }

        int numBits = to - from + 1;
        int mask = (1 << numBits) - 1;

        return (value >>> from) & mask;
    }
}
