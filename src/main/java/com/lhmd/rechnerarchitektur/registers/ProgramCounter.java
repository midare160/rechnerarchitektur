package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.changes.*;
import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.ProgramMemory;
import com.lhmd.rechnerarchitektur.values.IntBox;

import java.util.*;

public class ProgramCounter {
    private final IntBox pclRegister;
    private final IntBox pclathRegister;
    private final ChangeManager changeManager;
    private final ChangedEvent<Integer> changedEvent;

    private int highOrderBits;

    public ProgramCounter(IntBox pclRegister, IntBox pclathRegister) {
        this.pclRegister = Objects.requireNonNull(pclRegister);
        this.pclathRegister = Objects.requireNonNull(pclathRegister);
        this.changeManager = new ChangeManager();
        this.changedEvent = new ChangedEvent<>();

        pclRegister.changedEvent().addListener(this::onPclChanged);
    }

    public ChangedEvent<Integer> changedEvent() {
        return changedEvent;
    }

    /**
     * Concats the 5 upper bits with PCL bits <7:0>.
     */
    public int get() {
        return IntUtils.concatBits(highOrderBits, pclRegister.get(), 8);
    }

    /**
     * Increments PC by one.
     * If the result exceeds {@code ProgramMemory.MAX_SIZE}, PC is wrapped around.
     */
    public void increment() {
        var result = (get() + 1) % ProgramMemory.MAX_SIZE;
        override(result);
    }

    /**
     * The eleven bit immediate value is loaded into PC bits <10:0>.
     * The upper bits of PC are loaded from PCLATH<4:3>.
     *
     * @param value the eleven bit address
     */
    public void fromJump(int value) {
        var pclathPart = IntUtils.bitRange(pclathRegister.get(), 3, 4);
        var jumpPart = IntUtils.bitRange(value, 8, 10);

        changedEvent.fire(this::get, () -> {
            highOrderBits = IntUtils.concatBits(pclathPart, jumpPart, 3);
            setPclInternal(IntUtils.bitRange(value, 0, 7));
        });
    }

    /**
     * The thirteen bit immediate value is loaded into PC.
     *
     * @param value the thirteen bit address
     */
    public void override(int value) {
        changedEvent.fire(this::get, () -> {
            highOrderBits = IntUtils.bitRange(value, 8, 12);
            setPclInternal(IntUtils.bitRange(value, 0, 7));
        });
    }

    private void onPclChanged(Integer oldValue, Integer newValue) {
        if (changeManager.isChanging()) {
            return;
        }

        changedEvent.fire(this::get, () -> {
            highOrderBits = IntUtils.bitRange(pclathRegister.get(), 0, 4);
        });
    }

    private void setPclInternal(int value) {
        try (var ignored = changeManager.beginChange()) {
            pclRegister.set(value);
        }
    }
}
