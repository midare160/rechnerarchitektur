package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.registers.StatusRegister;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * W register is cleared. Zero bit (Z) is set.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Clrw extends InstructionBase {
    private final WRegister wRegister;
    private final StatusRegister statusRegister;

    public Clrw(StatusRegister statusRegister, WRegister wRegister) {
        this.statusRegister = statusRegister;
        this.wRegister = wRegister;
    }

    @Override
    public void execute() {
        wRegister.set(0);
        statusRegister.setZ(true);
    }
}
