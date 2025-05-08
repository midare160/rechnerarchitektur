package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * The contents of W register are AND’ed with the eight bit literal 'k'. The result is placed in the W register.
 */
public class Andlw extends InstructionBase {
    private final int literal;

    public Andlw(int instruction) {
        super(instruction);

        literal = IntUtils.bitRange(instruction, 0, 7);
    }

    @Override
    public void execute(ExecutionContext context) {
        var result = context.alu().and(getW(context), literal);
        setW(context, result);
    }
}
