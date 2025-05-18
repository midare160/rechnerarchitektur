package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.computing.Alu;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.StatusRegister;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The contents of register ’f’ are incremented.
 * If ’d’ is 0 the result is placed in the W register.
 * If ’d’ is 1 the result is placed back in register ’f’.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Incf extends InstructionBase {
    private final DataMemory dataMemory;
    private final StatusRegister statusRegister;
    private final WRegister wRegister;

    private int address;
    private boolean destination;

    public Incf(DataMemory dataMemory, StatusRegister statusRegister, WRegister wRegister) {
        this.dataMemory = dataMemory;
        this.statusRegister = statusRegister;
        this.wRegister = wRegister;
    }

    @Override
    public void execute() {
        var register = dataMemory.getRegister(address);
        var target = destination ? register : wRegister;

        var result = Math.floorMod(register.get() + 1, DataMemory.REGISTER_MAX_SIZE);

        statusRegister.updateZ(result);
        target.set(result);
    }

    @Override
    protected void onInitialized() {
        address = IntUtils.bitRange(getInstruction(), 0, 6);
        destination = IntUtils.isBitSet(getInstruction(), 7);
    }
}
