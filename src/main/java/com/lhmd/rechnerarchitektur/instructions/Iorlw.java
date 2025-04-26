package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * The contents of the W register is OR’ed with the eight bit literal 'k'.
 * The result is placed in the W register.
 */
public class Iorlw extends InstructionBase {
    private final int literal;

    public Iorlw(int instruction) {
        super(instruction);

        literal = IntUtils.bitRange(instruction, 0, 7);
    }

    @Override
    public void execute(ExecutionParams params) {
        var result = getW(params) | literal;

        setW(params, result);
        updateZ(params, result);
    }
}
