package com.lhmd.rechnerarchitektur.computing;

import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.StatusRegister;

import java.util.Objects;

public class Alu {
    private final StatusRegister statusRegister;

    public Alu(StatusRegister statusRegister) {
        this.statusRegister = Objects.requireNonNull(statusRegister);
    }

    public int add(int a, int b) {
        var result = Math.floorMod(a + b, DataMemory.REGISTER_SIZE + 1);

        updateC_Add(a, b);
        updateDC_Add(a, b);
        updateZ(result);

        return result;
    }

    public int sub(int a, int b) {
        var result = Math.floorMod(a - b, DataMemory.REGISTER_SIZE + 1);

        updateC_Sub(a, b);
        updateDC_Sub(a, b);
        updateZ(result);

        return result;
    }

    public int and(int a, int b) {
        var result = a & b;

        updateZ(result);

        return result;
    }

    public int or(int a, int b) {
        var result = a | b;

        updateZ(result);

        return result;
    }

    public int xor(int a, int b) {
        var result = a ^ b;

        updateZ(result);

        return result;
    }

    public int not(int a) {
        var result = a ^ DataMemory.REGISTER_SIZE;

        updateZ(result);

        return result;
    }

    private void updateC_Add(int a, int b) {
        statusRegister.setC(a + b > DataMemory.REGISTER_SIZE);
    }

    private void updateC_Sub(int a, int b) {
        statusRegister.setC(b >= a);
    }

    private void updateDC_Add(int a, int b) {
        // Mask to get lower 4 bits (nibble)
        var lowerNibbleSum = (a & 0x0F) + (b & 0x0F);
        var carryOccured = lowerNibbleSum > 0x0F;

        // DC = true if carry from bit 3
        statusRegister.setDC(carryOccured);
    }

    private void updateDC_Sub(int a, int b) {
        // For subtraction, polarity is reversed
        var noBorrowOccured = (a & 0x0F) >= (b & 0x0F);

        // DC = true if no borrow from bit 3
        statusRegister.setDC(noBorrowOccured);
    }

    private void updateZ(int result) {
        statusRegister.setZ(result == 0);
    }
}
