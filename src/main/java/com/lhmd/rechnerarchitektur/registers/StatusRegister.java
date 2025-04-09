package com.lhmd.rechnerarchitektur.registers;

import javafx.beans.property.IntegerProperty;

public class StatusRegister extends Register {
    private static final int C_INDEX = 0;
    private static final int DC_INDEX = 1;
    private static final int Z_INDEX = 2;
    private static final int RP0_INDEX = 5;

    public StatusRegister(IntegerProperty register) {
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
}
