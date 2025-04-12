package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.*;

public abstract class Register {
    protected final IntBox register;

    public Register(IntBox register) {
        this.register = register;
    }

    public void addListener(ChangeListener<Integer> listener) {
        register.addListener(listener);
    }

    public void removeListener(ChangeListener<Integer> listener) {
        register.removeListener(listener);
    }

    protected boolean isBitSet(int index) {
        return IntUtils.isBitSet(register.get(), index);
    }

    protected void setBit(int index, boolean value) {
        register.set(IntUtils.changeBit(register.get(), index, value));
    }
}
