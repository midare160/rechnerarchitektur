package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;

public abstract class Register {
    protected final IntegerProperty register;

    public Register(IntegerProperty register) {
        this.register = register;
    }

    public void addListener(ChangeListener<? super Number> listener) {
        register.addListener(listener);
    }

    public void removeListener(ChangeListener<? super Number> listener) {
        register.removeListener(listener);
    }

    protected boolean isBitSet(int index){
        return IntUtils.isBitSet(register.get(), index);
    }

    protected void setBit(int index, boolean value) {
        register.set(IntUtils.changeBit(register.get(), index, value));
    }
}
