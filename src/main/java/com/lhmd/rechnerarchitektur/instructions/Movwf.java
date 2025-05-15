package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Move data from W register to register 'f'.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Movwf extends InstructionBase {
    private final DataMemory dataMemory;
    private final WRegister wRegister;

    private int address;

    public Movwf(DataMemory dataMemory, WRegister wRegister) {
        this.dataMemory = dataMemory;
        this.wRegister = wRegister;
    }

    @Override
    public void execute() {
        var register = dataMemory.getRegister(address);
        register.set(wRegister.get());
    }

    @Override
    protected void onInitialized() {
        address = IntUtils.bitRange(getInstruction(), 0, 6);
    }
}
