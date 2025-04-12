package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.memory.WRegister;

public abstract class Instruction {
    private final int instruction;

    public Instruction(int instruction) {
        if (instruction > 0b11_1111_1111_1111) {
            throw new IllegalArgumentException("Instruction may only be 14 bits wide. Parameter was " + instruction);
        }

        this.instruction = instruction;
    }

    public int getInstruction() {
        return instruction;
    }

    public abstract void execute();

    protected void setW(int value) {
        WRegister.instance().set(value);
    }

    protected int getW() {
        return WRegister.instance().get();
    }

    protected void setC_Add(int a, int b) {
        DataMemory.instance().status().setC(a + b > 255);
    }

    protected void setC_Sub(int a, int b) {
        DataMemory.instance().status().setC(b >= a);
    }

    protected void setDC_Add(int a, int b) {
        // Mask to get lower 4 bits (nibble)
        var lowerNibbleSum = (a & 0x0F) + (b & 0x0F);
        var carryOccured = lowerNibbleSum > 0x0F;

        // DC = true if carry from bit 3
        DataMemory.instance().status().setDC(carryOccured);
    }

    protected void setDC_Sub(int a, int b) {
        // For subtraction, polarity is reversed
        var noBorrowOccured = (a & 0x0F) >= (b & 0x0F);

        // DC = true if no borrow from bit 3
        DataMemory.instance().status().setDC(noBorrowOccured);
    }

    protected void setZ(int result) {
        DataMemory.instance().status().setZ(result == 0);
    }
}
