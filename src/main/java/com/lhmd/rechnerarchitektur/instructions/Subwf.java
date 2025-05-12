package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * Subtract (2’s complement method) contents of W register from register 'f'.
 * If 'd' is 0 the result is stored in the W register.
 * If 'd' is 1 the result is stored back in register 'f'.
 */
public class Subwf extends InstructionBase {
    private final int address;
    private final boolean destination;

    public Subwf(int instruction) {
        super(instruction);

        address = IntUtils.bitRange(instruction, 0, 6);
        destination = IntUtils.isBitSet(instruction, 7);
    }

    @Override
    public void execute(ExecutionContext context) {
        var register = context.dataMemory().getRegister(address);
        var target = destination ? register : context.dataMemory().W();

        var result = context.alu().sub(register.get(), getW(context));
        target.set(result);
    }
}
