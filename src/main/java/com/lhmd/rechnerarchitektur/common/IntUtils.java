package com.lhmd.rechnerarchitektur.common;

public final class IntUtils {
    private IntUtils() {
    }

    /**
     * Like {@link Integer#parseInt(String)}, but will return {@code null} instead of throwing an exception.
     */
    public static Integer tryParse(String value) {
        return tryParse(value, 10);
    }

    /**
     * Like {@link Integer#parseInt(String, int)}, but will return {@code null} instead of throwing an exception.
     */
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
     * Tries to decode the passed {@code value}.
     * <p>Supported formats:</p>
     * <ul>
     *     <li>Decimal: {@code 1}, {@code 1d}</li>
     *     <li>Hex: {@code 0x1}, {@code 1h}</li>
     *     <li>Binary: {@code 0b1}</li>
     * </ul>
     */
    public static Integer tryDecode(String value) {
        if (StringUtils.isNullOrWhitespace(value)) {
            return null;
        }

        value = value.toLowerCase();

        if (value.startsWith("0x")) {
            return tryParse(value.substring(2), 16);
        }

        if (value.endsWith("h")) {
            return tryParse(value.substring(0, value.length() - 1), 16);
        }

        if (value.startsWith("0b")) {
            return tryParse(value.substring(2), 2);
        }

        if (value.endsWith("d")) {
            return tryParse(value.substring(0, value.length() - 1), 10);
        }

        return tryParse(value, 10);
    }

    public static boolean isBitSet(int value, int index) {
        requireValidBitIndex(index);

        return ((value >> index) & 1) != 0;
    }

    public static int changeBit(int value, int index, boolean isSet) {
        requireValidBitIndex(index);

        if (isSet) {
            return setBit(value, index);
        }

        return clearBit(value, index);
    }

    public static int setBit(int value, int index) {
        requireValidBitIndex(index);

        return value | (1 << index);
    }

    public static int clearBit(int value, int index) {
        requireValidBitIndex(index);

        return value & ~(1 << index);
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

    /**
     * Concatenates the binary representations of two integers.
     *
     * <p>The bits of {@code a} are placed in the higher-order positions, followed by
     * the bits of {@code b} in the lower-order positions.
     * <p>
     * Note: This method assumes non-negative integers. Negative values may produce
     * unexpected results due to two's complement representation.
     *
     * @param a       the first integer (bits will be placed in higher-order positions)
     * @param b       the second integer (bits will be placed in lower-order positions)
     * @param bLength the number of bits from {@code b} that should be concatenated with {@code a}
     * @return an integer representing the bitwise concatenation of {@code a} and {@code b}
     */
    public static int concatBits(int a, int b, int bLength) {
        return (a << bLength) | b;
    }

    /**
     * Sets all bits using the passed {@code pattern}, e.g. {@code --uu1xx0}.
     */
    public static int changeBits(int value, String pattern) {
        for (var i = pattern.length() - 1; i >= 0; i--) {
            var bitIndex = pattern.length() - i - 1;

            value = switch (pattern.charAt(i)) {
                case 'u', 'x', '-' -> value;
                case '1' -> setBit(value, bitIndex);
                case '0' -> clearBit(value, bitIndex);
                default -> throw new IllegalArgumentException("Unexpected value: " + pattern);
            };
        }

        return value;
    }

    public static int requireValidBitIndex(int index) {
        if (index < 0 || index > 31) {
            throw new IndexOutOfBoundsException("Index must be between 0 and 31. Index = " + index);
        }

        return index;
    }

    public static int requireLargerZero(int value) {
        if (value <= 0) {
            throw new IndexOutOfBoundsException("Value must be greater than zero. Value = " + value);
        }

        return value;
    }
}
