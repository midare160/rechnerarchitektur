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
    public void execute(ExecutionParams params) {
        var currentW = getW(params);
        var result = (currentW + literal) % 256;

        setW(params, result);

        updateC_Add(params, currentW, literal);
        updateDC_Add(params, currentW, literal);
        updateZ(params, result);
    }
}
