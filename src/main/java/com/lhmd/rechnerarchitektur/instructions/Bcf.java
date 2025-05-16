package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bit ’b’ in register ’f’ is cleared.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Bcf extends InstructionBase {
    private final DataMemory dataMemory;

    private int address;
    private int bit;

    public Bcf(DataMemory dataMemory) {
        this.dataMemory = dataMemory;
    }

    @Override
    public void execute() {
        var register = dataMemory.getRegister(address);

        var result = IntUtils.clearBit(register.get(), bit);
        register.set(result);
    }

    @Override
    protected void onInitialized() {
        address = IntUtils.bitRange(getInstruction(), 0, 6);
        bit = IntUtils.bitRange(getInstruction(), 7, 9);
    }
}
