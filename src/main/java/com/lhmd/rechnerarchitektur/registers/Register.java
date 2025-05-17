package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.*;
import com.lhmd.rechnerarchitektur.values.IntBox;

import java.util.Objects;

public abstract class Register {
    private final IntBox register;

    public Register(IntBox register) {
        this.register = Objects.requireNonNull(register);
    }

    protected IntBox getRegister() {
        return register;
    }

    protected boolean isBitSet(int index) {
        return IntUtils.isBitSet(register.get(), index);
    }

    protected void setBit(int index, boolean value) {
        register.set(IntUtils.changeBit(register.get(), index, value));
    }
}
