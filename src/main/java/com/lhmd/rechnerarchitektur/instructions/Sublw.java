package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * The contents of W register is subtracted from the eight bit literal 'k'.
 * The result is placed in the W register.
 */
public class Sublw extends Instruction {
    private final int literal;

    public Sublw(int instruction) {
        super(instruction);

        literal = IntUtils.bitRange(instruction, 0, 7);
    }

    @Override
    public void execute(ExecutionParams params) {
        var currentW = getW(params);
        var result = Math.floorMod(literal - currentW, 256);

        setW(params, result);

        updateC_Sub(params, currentW, literal);
        updateDC_Sub(params, currentW, literal);
        updateZ(params, result);
    }
}
