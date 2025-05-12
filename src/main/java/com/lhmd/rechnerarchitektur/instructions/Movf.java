package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * The contents of register f is moved to a destination dependant upon the status of d.
 * If d = 0, destination is W register. If d = 1, the destination is file register f itself.
 * d = 1 is useful to test a file register since status flag Z is affected.
 */
public class Movf extends InstructionBase {
    private final int address;
    private final boolean destination;

    public Movf(int instruction) {
        super(instruction);

        address = IntUtils.bitRange(instruction, 0, 6);
        destination = IntUtils.isBitSet(instruction, 7);
    }

    @Override
    public void execute(ExecutionContext context) {
        var register = context.dataMemory().getRegister(address);
        var target = destination ? register : context.dataMemory().W();

        target.set(register.get());
        context.dataMemory().status().updateZ(target.get());
    }
}
