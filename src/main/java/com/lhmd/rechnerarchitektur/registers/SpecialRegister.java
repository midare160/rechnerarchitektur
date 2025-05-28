package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.values.IntBox;

public abstract class SpecialRegister extends IntBox {
    public abstract int getAddress();

    public boolean isMirrored() {
        return false;
    }
}
