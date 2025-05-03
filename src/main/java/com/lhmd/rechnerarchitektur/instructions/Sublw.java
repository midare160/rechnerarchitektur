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
        var currentW = getW(context);
        var result = Math.floorMod(literal - currentW, 256);

        setW(context, result);

        updateC_Sub(context, currentW, literal);
        updateDC_Sub(context, currentW, literal);
        updateZ(context, result);
    }
}
