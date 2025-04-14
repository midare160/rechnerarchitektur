package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.changes.ChangeManager;
import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.ProgramMemory;
import com.lhmd.rechnerarchitektur.values.IntBox;

public class ProgramCounter {
    private final IntBox pclRegister;
    private final IntBox pclathRegister;
    private final ChangeManager changeManager;

    private int highOrderBits;

    public ProgramCounter(IntBox pclRegister, IntBox pclathRegister) {
        this.pclRegister = pclRegister;
        this.pclathRegister = pclathRegister;
        this.changeManager = new ChangeManager();

        pclRegister.addListener(this::onPclChanged);
    }

    public int get() {
        return IntUtils.concatBits(highOrderBits, pclRegister.get(), 8);
    }

    public void increment() {
        var result = (pclRegister.get() + 1) % ProgramMemory.MAX_SIZE;

        try (var ignored = changeManager.beginChange()) {
            pclRegister.set(result);
        }
    }

    public void fromJump(int value) {
        var pclathPart = IntUtils.bitRange(pclathRegister.get(), 3, 4);
        var jumpPart = IntUtils.bitRange(value, 8, 10);

        highOrderBits = IntUtils.concatBits(pclathPart, jumpPart, 3);

        try (var ignored = changeManager.beginChange()) {
            pclRegister.set(IntUtils.bitRange(value, 0, 7));
        }
    }

    public void override(int value) {
        highOrderBits = IntUtils.bitRange(value, 8, 12);

        try (var ignored = changeManager.beginChange()) {
            pclRegister.set(IntUtils.bitRange(value, 0, 7));
        }
    }

    private void onPclChanged(int oldValue, int newValue) {
        if (changeManager.isChanging()) {
            return;
        }

        highOrderBits = pclathRegister.get();
    }
}
