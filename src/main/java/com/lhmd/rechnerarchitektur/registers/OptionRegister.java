package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.values.IntBox;

public class OptionRegister extends Register {
    private static final int PSA_INDEX = 3;
    private static final int T0SE_INDEX = 4;
    private static final int T0CS_INDEX = 5;
    private static final int INTEDG_INDEX = 6;
    private static final int RBPU_INDEX = 7;

    public OptionRegister(IntBox register) {
        super(register);
    }

    public int getPs02() {
        return IntUtils.bitRange(getRegister().get(), 0, 2);
    }
}
