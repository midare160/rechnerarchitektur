package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * The contents of the W register are added to the eight bit literal ’k’ and the result is placed in the W register.
 */
public class Addlw extends InstructionBase {
    private final int literal;

    public Addlw(int instruction) {
        super(instruction);

        literal = IntUtils.bitRange(instruction, 0, 7);
    }

    @Override
    public void execute(ExecutionContext context) {
        var result = context.alu().add(getW(context), literal);
        setW(context, result);
    }
}
