package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bit ’b’ in register ’f’ is set.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Bsf extends InstructionBase {
    private final DataMemory dataMemory;

    private int address;
    private int bit;

    public Bsf(DataMemory dataMemory) {
        this.dataMemory = dataMemory;
    }

    @Override
    public void execute() {
        var register = dataMemory.getRegister(address);

        var result = IntUtils.setBit(register.get(), bit);
        register.set(result);
    }

    @Override
    protected void onInitialized() {
        address = IntUtils.bitRange(getInstruction(), 0, 6);
        bit = IntUtils.bitRange(getInstruction(), 7, 9);
    }
}
