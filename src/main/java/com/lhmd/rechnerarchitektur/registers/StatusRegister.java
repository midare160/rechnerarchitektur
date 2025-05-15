package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.values.IntBox;

public class StatusRegister extends Register {
    private static final int C_INDEX = 0;
    private static final int DC_INDEX = 1;
    private static final int Z_INDEX = 2;
    private static final int RP0_INDEX = 5;

    public StatusRegister(IntBox register) {
        super(register);
    }

    public boolean getC() {
        return isBitSet(C_INDEX);
    }

    public void setC(boolean value) {
        setBit(C_INDEX, value);
    }

    public boolean getDC() {
        return isBitSet(DC_INDEX);
    }

    public void setDC(boolean value) {
        setBit(DC_INDEX, value);
    }

    public boolean getZ() {
        return isBitSet(Z_INDEX);
    }

    public void setZ(boolean value) {
        setBit(Z_INDEX, value);
    }

    public boolean getRP0() {
        return isBitSet(RP0_INDEX);
    }

    public void setRP0(boolean value) {
        setBit(RP0_INDEX, value);
    }

    public void updateC_Add(int a, int b) {
        setC(a + b >= DataMemory.REGISTER_SIZE);
    }

    public void updateC_Sub(int a, int b) {
        setC(b > a);
    }

    public void updateDC_Add(int a, int b) {
        // Mask to get lower 4 bits (nibble)
        var lowerNibbleSum = (a & 0x0F) + (b & 0x0F);
        var carryOccured = lowerNibbleSum > 0x0F;

        // DC = true if carry from bit 3
        setDC(carryOccured);
    }

    public void updateDC_Sub(int a, int b) {
        // For subtraction, polarity is reversed
        var noBorrowOccured = (a & 0x0F) >= (b & 0x0F);

        // DC = true if no borrow from bit 3
        setDC(noBorrowOccured);
    }

    public void updateZ(int result) {
        setZ(result == 0);
    }
}
