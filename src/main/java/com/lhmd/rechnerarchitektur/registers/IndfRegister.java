package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.events.ChangeListener;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.values.IntBox;

/**
 * The INDF register is not a physical register.
 * Addressing INDF actually addresses the register whose address is contained in the FSR register (FSR is a pointer).
 * This is indirect addressing.
 */
public class IndfRegister {
    private final DataMemory dataMemory;
    private final IntBox indf;
    private final IntBox fsr;

    private final ChangeListener<Integer> fsrPointerChangeListener;

    public IndfRegister(DataMemory dataMemory) {
        this.dataMemory = dataMemory;
        this.indf = dataMemory.getRegister(SpecialAdresses.INDF);
        this.fsr = dataMemory.getRegister(SpecialAdresses.FSR);

        this.fsrPointerChangeListener = this::onFsrPointerChanged;

        this.indf.onChanged().addListener(this::onIndfChanged);
        this.fsr.onChanged().addListener(this::onFsrChanged);
    }

    private void onIndfChanged(Integer oldValue, Integer newValue) {
        dataMemory.getRegister(fsr.get()).setValue(newValue);
    }

    private void onFsrChanged(Integer oldValue, Integer newValue) {
        dataMemory.getRegister(oldValue).onChanged().removeListener(fsrPointerChangeListener);

        var newRegister = dataMemory.getRegister(newValue);
        newRegister.onChanged().addListener(fsrPointerChangeListener);

        indf.set(newRegister.get());
    }

    private void onFsrPointerChanged(Integer oldValue, Integer newValue) {
        indf.setValue(newValue);
    }
}
