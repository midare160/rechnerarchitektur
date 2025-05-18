package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.StatusRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The contents of register ’f’ are cleared and the Z bit is set.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Clrf extends InstructionBase {
    private final DataMemory dataMemory;
    private final StatusRegister statusRegister;

    private int address;

    public Clrf(DataMemory dataMemory, StatusRegister statusRegister) {
        this.dataMemory = dataMemory;
        this.statusRegister = statusRegister;
    }

    @Override
    public void execute() {
        var register = dataMemory.getRegister(address);

        register.set(0);
        statusRegister.setZ(true);
    }

    @Override
    protected void onInitialized() {
        address = IntUtils.bitRange(getInstruction(), 0, 6);
    }
}
