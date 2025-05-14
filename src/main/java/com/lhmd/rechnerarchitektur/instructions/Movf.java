package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The contents of register f is moved to a destination dependant upon the status of d.
 * If d = 0, destination is W register. If d = 1, the destination is file register f itself.
 * d = 1 is useful to test a file register since status flag Z is affected.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Movf extends InstructionBase {
    private final DataMemory dataMemory;
    private final WRegister wRegister;

    private int address;
    private boolean destination;

    public Movf(DataMemory dataMemory, WRegister wRegister) {
        this.dataMemory = dataMemory;
        this.wRegister = wRegister;
    }

    @Override
    public void execute() {
        var register = dataMemory.getRegister(address);
        var target = destination ? register : wRegister;

        target.set(register.get());
        dataMemory.status().updateZ(target.get());
    }

    @Override
    protected void onInitialized() {
        address = IntUtils.bitRange(getInstruction(), 0, 6);
        destination = IntUtils.isBitSet(getInstruction(), 7);
    }
}
