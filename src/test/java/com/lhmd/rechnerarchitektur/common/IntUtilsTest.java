package com.lhmd.rechnerarchitektur.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntUtilsTest {

    private static final int RANGE_BITS = 0b1111_0000_1010_0101_1100_0011_1001_0110;

    @Test
    public void bitRange_lastFourBits() {
        var expected = 0b0110;
        var actual = IntUtils.bitRange(RANGE_BITS, 0, 3);

        assertEquals(expected, actual);
    }

    @Test
    public void bitRange_firstEightBits() {
        var expected = 0b1111_0000;
        var actual = IntUtils.bitRange(RANGE_BITS, 24, 31);

        assertEquals(expected, actual);
    }

    @Test
    public void bitRange_middleSixteenBits() {
        var expected = 0b1010_0101_1100_0011;
        var actual = IntUtils.bitRange(RANGE_BITS, 8, 23);

        assertEquals(expected, actual);
    }

    @Test
    public void bitRange_tenthBit() {
        var expected = 0b1;
        var actual = IntUtils.bitRange(RANGE_BITS, 9, 9);

        assertEquals(expected, actual);
    }

    @Test
    public void bitRange_twentySecondBit() {
        var expected = 0b0;
        var actual = IntUtils.bitRange(RANGE_BITS, 22, 22);

        assertEquals(expected, actual);
    }

    @Test
    public void concatBits_onesPlusZeros() {
        var expected = 0b1111_0000;
        var actual = IntUtils.concatBits(0b1111, 0b0000, 4);

        assertEquals(expected, actual);
    }

    @Test
    public void concatBits_ZerosPlusOnes() {
        var expected = 0b0111;
        var actual = IntUtils.concatBits(0b000, 0b111, 3);

        assertEquals(expected, actual);
    }

    @Test
    public void concatBits_biggerA() {
        var expected = 0b1_1010_0011_0110;
        var actual = IntUtils.concatBits(0b1_1010_0011, 0b0110, 4);

        assertEquals(expected, actual);
    }

    @Test
    public void concatBits_biggerB() {
        var expected = 0b1_1010_0011_0110;
        var actual = IntUtils.concatBits(0b1_1010, 0b0011_0110, 8);

        assertEquals(expected, actual);
    }

    @Test
    public void changeBits_allPossibleChars_setsCorrectly() {
        var expected = 0b0111_0010;
        var actual = IntUtils.changeBits(0b1011_0010, "01xx--uu");

        assertEquals(expected, actual);
    }

    @Test
    public void changeBits_invalidChar_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> IntUtils.changeBits(69, "01xx-iuu"));
    }
}
