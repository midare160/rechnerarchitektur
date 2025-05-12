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

        statusRegister.updateC_Add(a, b);
        statusRegister.updateDC_Add(a, b);
        statusRegister.updateZ(result);

        return result;
    }

    public int sub(int a, int b) {
        var result = Math.floorMod(a - b, DataMemory.REGISTER_SIZE + 1);

        statusRegister.updateC_Sub(a, b);
        statusRegister.updateDC_Sub(a, b);
        statusRegister.updateZ(result);

        return result;
    }

    public int and(int a, int b) {
        var result = a & b;

        statusRegister.updateZ(result);

        return result;
    }

    public int or(int a, int b) {
        var result = a | b;

        statusRegister.updateZ(result);

        return result;
    }

    public int xor(int a, int b) {
        var result = a ^ b;

        statusRegister.updateZ(result);

        return result;
    }

    public int not(int a) {
        var result = a ^ DataMemory.REGISTER_SIZE;

        statusRegister.updateZ(result);

        return result;
    }

    public int dec(int a) {
        var result = Math.floorMod(a - 1, DataMemory.REGISTER_SIZE + 1);

        statusRegister.updateZ(result);

        return result;
    }

    public int inc(int a) {
        var result = Math.floorMod(a + 1, DataMemory.REGISTER_SIZE + 1);

        statusRegister.updateZ(result);

        return result;
    }
}
