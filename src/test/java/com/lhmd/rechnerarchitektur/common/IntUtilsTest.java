package com.lhmd.rechnerarchitektur.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntUtilsTest {

    private static final int BITS = 0b1111_0000_1010_0101_1100_0011_1001_0110;

    @Test
    public void bitRange_lastFour() {
        var expected = 0b0110;
        var actual = IntUtils.bitRange(BITS, 0, 3);

        assertEquals(expected, actual);
    }

    @Test
    public void bitRange_firstEight() {
        var expected = 0b1111_0000;
        var actual = IntUtils.bitRange(BITS, 24, 31);

        assertEquals(expected, actual);
    }

    @Test
    public void bitRange_middleSixteen() {
        var expected = 0b1010_0101_1100_0011;
        var actual = IntUtils.bitRange(BITS, 8, 23);

        assertEquals(expected, actual);
    }

    @Test
    public void bitRange_TenthBit() {
        var expected = 0b1;
        var actual = IntUtils.bitRange(BITS, 9, 9);

        assertEquals(expected, actual);
    }

    @Test
    public void bitRange_TwentySecond() {
        var expected = 0b0;
        var actual = IntUtils.bitRange(BITS, 22, 22);

        assertEquals(expected, actual);
    }
}
