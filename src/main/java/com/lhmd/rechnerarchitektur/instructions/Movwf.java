package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * Move data from W register to register 'f'.
 */
public class Movwf extends InstructionBase {
    private final int address;

    public Movwf(int instruction) {
        super(instruction);

        address = IntUtils.bitRange(instruction, 0, 6);
    }

    @Override
    public void execute(ExecutionContext context) {
        var register = context.dataMemory().getRegister(address);
        register.set(getW(context));
    }
}
