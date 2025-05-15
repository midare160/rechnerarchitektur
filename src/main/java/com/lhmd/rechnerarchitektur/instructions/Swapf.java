package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The upper and lower nibbles of contents of register 'f' are exchanged.
 * If 'd' is 0 the result is placed in W register.
 * If 'd' is 1 the result is placed in register 'f'.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Swapf extends InstructionBase {
    private final DataMemory dataMemory;
    private final WRegister wRegister;

    private int address;
    private boolean destination;

    public Swapf(DataMemory dataMemory, WRegister wRegister) {
        this.dataMemory = dataMemory;
        this.wRegister = wRegister;
    }

    @Override
    public void execute() {
        var register = dataMemory.getRegister(address);
        var target = destination ? register : wRegister;

        var a = IntUtils.bitRange(register.get(), 0, 3);
        var b = IntUtils.bitRange(register.get(), 4, 7);
        var result = IntUtils.concatBits(a, b, 4);

        target.set(result);
    }

    @Override
    protected void onInitialized() {
        address = IntUtils.bitRange(getInstruction(), 0, 6);
        destination = IntUtils.isBitSet(getInstruction(), 7);
    }
}
