package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.values.IntBox;

public abstract class SpecialRegister extends IntBox {
    public abstract int getAddress();

    public boolean isMirrored() {
        return false;
    }

    public boolean isBitSet(int index) {
        return IntUtils.isBitSet(get(), index);
    }

    public void setBit(int index, boolean value) {
        set(IntUtils.changeBit(get(), index, value));
    }
}
