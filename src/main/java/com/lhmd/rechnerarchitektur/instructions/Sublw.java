package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * The contents of W register is subtracted from the eight bit literal 'k'.
 * The result is placed in the W register.
 */
public class Sublw extends InstructionBase {
    private final int literal;

    public Sublw(int instruction) {
        super(instruction);

        literal = IntUtils.bitRange(instruction, 0, 7);
    }

    @Override
    public void execute(ExecutionContext context) {
        var result = context.alu().sub(literal, getW(context));
        setW(context, result);
    }
}
