package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.changes.*;
import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.ProgramMemory;
import com.lhmd.rechnerarchitektur.values.IntBox;

import java.util.*;

public class ProgramCounter extends IntBox {
    private final IntBox pclRegister;
    private final IntBox pclathRegister;
    private final ChangeManager changeManager;

    public ProgramCounter(IntBox pclRegister, IntBox pclathRegister) {
        this.pclRegister = Objects.requireNonNull(pclRegister);
        this.pclathRegister = Objects.requireNonNull(pclathRegister);
        this.changeManager = new ChangeManager();

        onChanged().addListener(this::onPcChanged);
        pclRegister.onChanged().addListener(this::onPclChanged);
    }

    /**
     * Increments PC by one.
     * If the result exceeds {@code ProgramMemory.MAX_SIZE}, PC is wrapped around.
     */
    public void increment() {
        var result = (get() + 1) % ProgramMemory.MAX_SIZE;
        set(result);
    }

    /**
     * The eleven bit immediate value is loaded into PC bits <10:0>.
     * The upper bits of PC are loaded from PCLATH<4:3>.
     *
     * @param value the eleven bit address
     */
    public void fromJump(int value) {
        var pclathPart = IntUtils.bitRange(pclathRegister.get(), 3, 4);
        var jumpPart = IntUtils.bitRange(value, 0, 10);
        var combined = IntUtils.concatBits(pclathPart, jumpPart, 11);

        set(combined);
    }

    private void onPcChanged(Integer oldValue, Integer newValue) {
        if (changeManager.isChanging()) {
            return;
        }

        try (var ignored = changeManager.beginChange()) {
            pclRegister.set(IntUtils.bitRange(newValue, 0, 7));
        }
    }

    private void onPclChanged(Integer oldValue, Integer newValue) {
        if (changeManager.isChanging()) {
            return;
        }

        var pclathPart = IntUtils.bitRange(pclathRegister.get(), 0, 4);
        var pclPart = IntUtils.bitRange(newValue, 0, 7);
        var combined = IntUtils.concatBits(pclathPart, pclPart, 8);

        set(combined);
    }
}
