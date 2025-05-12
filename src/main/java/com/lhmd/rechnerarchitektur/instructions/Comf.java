package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * The contents of register ’f’ are complemented.
 * If ’d’ is 0 the result is stored in W.
 * If ’d’ is 1 the result is stored back in register ’f’.
 */
public class Comf extends InstructionBase {
    private final int address;
    private final boolean destination;

    public Comf(int instruction) {
        super(instruction);

        address = IntUtils.bitRange(instruction, 0, 6);
        destination = IntUtils.isBitSet(instruction, 7);
    }

    @Override
    public void execute(ExecutionContext context) {
        var register = context.dataMemory().getRegister(address);
        var target = destination ? register : context.dataMemory().W();

        var result = context.alu().not(register.get());
        target.set(result);
    }
}
