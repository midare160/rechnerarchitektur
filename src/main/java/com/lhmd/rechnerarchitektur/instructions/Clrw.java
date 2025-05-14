package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * W register is cleared. Zero bit (Z) is set.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Clrw extends InstructionBase {
    private final DataMemory dataMemory;
    private final WRegister wRegister;

    public Clrw(DataMemory dataMemory, WRegister wRegister) {
        this.dataMemory = dataMemory;
        this.wRegister = wRegister;
    }

    @Override
    public void execute() {
        wRegister.set(0);
        dataMemory.status().setZ(true);
    }
}
