package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.execution.Alu;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The contents of register ’f’ are complemented.
 * If ’d’ is 0 the result is stored in W.
 * If ’d’ is 1 the result is stored back in register ’f’.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Comf extends InstructionBase {
    private final Alu alu;
    private final DataMemory dataMemory;
    private final WRegister wRegister;

    private int address;
    private boolean destination;

    public Comf(Alu alu, DataMemory dataMemory, WRegister wRegister) {
        this.alu = alu;
        this.dataMemory = dataMemory;
        this.wRegister = wRegister;
    }

    @Override
    public void execute() {
        var register = dataMemory.getRegister(address);
        var target = destination ? register : wRegister;

        var result = alu.not(register.get());
        target.set(result);
    }

    @Override
    protected void onInitialized() {
        address = IntUtils.bitRange(getInstruction(), 0, 6);
        destination = IntUtils.isBitSet(getInstruction(), 7);
    }
}
