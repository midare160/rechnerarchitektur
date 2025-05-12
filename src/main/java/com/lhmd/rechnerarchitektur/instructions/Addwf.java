package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * Add the contents of the W register with the contents of register ’f’.
 * If ’d’ is 0 the result is stored in the W register.
 * If ’d’ is 1 the result is stored back in register ’f’.
 */
public class Addwf extends InstructionBase {
    private final int address;
    private final boolean destination;

    public Addwf(int instruction) {
        super(instruction);

        address = IntUtils.bitRange(instruction, 0, 6);
        destination = IntUtils.isBitSet(instruction, 7);
    }

    @Override
    public void execute(ExecutionContext context) {
        var register = context.dataMemory().getRegister(address);
        var target = destination ? register : context.dataMemory().W();

        var result = context.alu().add(getW(context), register.get());
        target.set(result);
    }
}
