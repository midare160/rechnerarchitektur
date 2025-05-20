package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.events.ChangeListener;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * The INDF register is not a physical register.
 * Addressing INDF actually addresses the register whose address is contained in the FSR register (FSR is a pointer).
 * This is indirect addressing.
 */
@Component
public class IndfRegister extends SpecialRegister {
    private final FsrRegister fsrRegister;
    private final ChangeListener<Integer> fsrPointerChangeListener;

    private DataMemory dataMemory;

    public IndfRegister(FsrRegister fsrRegister) {
        this.fsrRegister = fsrRegister;

        this.fsrPointerChangeListener = this::onFsrPointerChanged;

        this.onChanged().addListener(this::onIndfChanged);
        this.fsrRegister.onChanged().addListener(this::onFsrChanged);
    }

    @Override
    public int getAddress() {
        return 0x00;
    }

    @Override
    public boolean isMirrored() {
        return true;
    }

    @Autowired
    @Lazy
    public void setDataMemory(DataMemory dataMemory) {
        this.dataMemory = dataMemory;
    }

    private void onIndfChanged(Integer oldValue, Integer newValue) {
        dataMemory.getRegister(fsrRegister.get()).set(newValue);
    }

    private void onFsrChanged(Integer oldValue, Integer newValue) {
        dataMemory.getRegister(oldValue).onChanged().removeListener(fsrPointerChangeListener);

        var newRegister = dataMemory.getRegister(newValue);
        newRegister.onChanged().addListener(fsrPointerChangeListener);

        set(newRegister.get());
    }

    private void onFsrPointerChanged(Integer oldValue, Integer newValue) {
        set(newValue);
    }
}
