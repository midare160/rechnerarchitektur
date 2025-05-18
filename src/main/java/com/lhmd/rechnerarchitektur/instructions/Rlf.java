package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.StatusRegister;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The contents of register ’f’ are rotated one bit to the left through the Carry Flag.
 * If ’d’ is 0 the result is placed in the W register.
 * If ’d’ is 1 the result is stored back in register ’f’.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Rlf extends InstructionBase {
    private final DataMemory dataMemory;
    private final StatusRegister statusRegister;
    private final WRegister wRegister;

    private int address;
    private boolean destination;

    public Rlf(DataMemory dataMemory, StatusRegister statusRegister, WRegister wRegister) {
        this.dataMemory = dataMemory;
        this.statusRegister = statusRegister;
        this.wRegister = wRegister;
    }

    @Override
    public void execute() {
        var register = dataMemory.getRegister(address);
        var target = destination ? register : wRegister;

        var result = IntUtils.changeBit(register.get() << 1, 0, statusRegister.getC());
        result = IntUtils.clearBit(result, 8);

        statusRegister.setC(IntUtils.isBitSet(register.get(), 7));
        target.set(result);
    }

    @Override
    protected void onInitialized() {
        address = IntUtils.bitRange(getInstruction(), 0, 6);
        destination = IntUtils.isBitSet(getInstruction(), 7);
    }
}
