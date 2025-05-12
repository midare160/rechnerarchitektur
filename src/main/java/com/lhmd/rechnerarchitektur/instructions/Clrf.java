package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * The contents of register ’f’ are cleared and the Z bit is set.
 */
public class Clrf extends InstructionBase {
    private final int address;

    public Clrf(int instruction) {
        super(instruction);

        address = IntUtils.bitRange(instruction, 0, 6);
    }

    @Override
    public void execute(ExecutionContext context) {
        var register = context.dataMemory().getRegister(address);

        register.set(0);
        context.dataMemory().status().setZ(true);
    }
}
