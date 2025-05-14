package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.computing.Alu;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Subtract (2’s complement method) contents of W register from register 'f'.
 * If 'd' is 0 the result is stored in the W register.
 * If 'd' is 1 the result is stored back in register 'f'.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Subwf extends InstructionBase {
    private final Alu alu;
    private final DataMemory dataMemory;
    private final WRegister wRegister;

    private int address;
    private boolean destination;

    public Subwf(Alu alu, DataMemory dataMemory, WRegister wRegister) {
        this.alu = alu;
        this.dataMemory = dataMemory;
        this.wRegister = wRegister;
    }

    @Override
    public void execute() {
        var register = dataMemory.getRegister(address);
        var target = destination ? register : wRegister;

        var result = alu.sub(register.get(), wRegister.get());
        target.set(result);
    }

    @Override
    protected void onInitialized() {
        address = IntUtils.bitRange(getInstruction(), 0, 6);
        destination = IntUtils.isBitSet(getInstruction(), 7);
    }
}
