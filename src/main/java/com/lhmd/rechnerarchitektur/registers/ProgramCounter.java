package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.common.*;
import com.lhmd.rechnerarchitektur.memory.ProgramMemory;

public class ProgramCounter {
    private final IntBox pclRegister;
    private final IntBox pclathRegister;

    private int highOrderBits;
    private boolean pclChanging;

    public ProgramCounter(IntBox pclRegister, IntBox pclathRegister) {
        this.pclRegister = pclRegister;
        this.pclathRegister = pclathRegister;

        pclRegister.addListener(this::onPclChanged);
    }

    public int get() {
        return IntUtils.concatBits(highOrderBits, pclRegister.get());
    }

    public void increment() {
        var result = (pclRegister.get() + 1) % ProgramMemory.MAX_SIZE;

        try (var ignored = getPclChangingTempValue()) {
            pclRegister.set(result);
        }
    }

    public void fromJump(int value) {
        var pclathPart = IntUtils.bitRange(pclathRegister.get(), 3, 4);
        var jumpPart = IntUtils.bitRange(value, 8, 10);

        highOrderBits = IntUtils.concatBits(pclathPart, jumpPart);

        try (var ignored = getPclChangingTempValue()) {
            pclRegister.set(IntUtils.bitRange(value, 0, 7));
        }
    }

    public void override(int value) {
        highOrderBits = IntUtils.bitRange(value, 8, 12);

        try (var ignored = getPclChangingTempValue()) {
            pclRegister.set(IntUtils.bitRange(value, 0, 7));
        }
    }

    private void onPclChanged(int oldValue, int newValue) {
        if (pclChanging) {
            return;
        }

        highOrderBits = pclathRegister.get();
    }

    private BooleanTempValue getPclChangingTempValue() {
        return new BooleanTempValue(pclChanging, c -> pclChanging = c);
    }
}
