package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.changes.ChangeManager;
import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.ProgramMemory;
import com.lhmd.rechnerarchitektur.values.IntBox;

import java.util.*;

public class ProgramCounter {
    private final IntBox pclRegister;
    private final IntBox pclathRegister;
    private final ChangeManager changeManager;

    private int highOrderBits;

    public ProgramCounter(IntBox pclRegister, IntBox pclathRegister) {
        this.pclRegister = Objects.requireNonNull(pclRegister);
        this.pclathRegister = Objects.requireNonNull(pclathRegister);
        this.changeManager = new ChangeManager();

        pclRegister.addListener(this::onPclChanged);
    }

    public int get() {
        return IntUtils.concatBits(highOrderBits, pclRegister.get(), 8);
    }

    public void increment() {
        var result = (get() + 1) % ProgramMemory.MAX_SIZE;
        override(result);
    }

    public void fromJump(int value) {
        var pclathPart = IntUtils.bitRange(pclathRegister.get(), 3, 4);
        var jumpPart = IntUtils.bitRange(value, 8, 10);

        highOrderBits = IntUtils.concatBits(pclathPart, jumpPart, 3);
        setPclInternal(IntUtils.bitRange(value, 0, 7));
    }

    public void override(int value) {
        highOrderBits = IntUtils.bitRange(value, 8, 12);
        setPclInternal(IntUtils.bitRange(value, 0, 7));
    }

    private void onPclChanged(Integer oldValue, Integer newValue) {
        if (changeManager.isChanging()) {
            return;
        }

        highOrderBits = IntUtils.bitRange(pclathRegister.get(), 0, 4);
    }

    private void setPclInternal(int value) {
        try (var ignored = changeManager.beginChange()) {
            pclRegister.set(value);
        }
    }
}
