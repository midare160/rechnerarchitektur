package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * The upper and lower nibbles of contents of register 'f' are exchanged.
 * If 'd' is 0 the result is placed in W register.
 * If 'd' is 1 the result is placed in register 'f'.
 */
public class Swapf extends InstructionBase {
    private final int address;
    private final boolean destination;

    public Swapf(int instruction) {
        super(instruction);

        address = IntUtils.bitRange(instruction, 0, 6);
        destination = IntUtils.isBitSet(instruction, 7);
    }

    @Override
    public void execute(ExecutionContext context) {
        var register = context.dataMemory().getRegister(address);
        var target = destination ? register : context.dataMemory().W();

        var a = IntUtils.bitRange(register.get(), 0, 3);
        var b = IntUtils.bitRange(register.get(), 4, 7);
        var result = IntUtils.concatBits(a, b, 4);

        target.set(result);
    }
}
