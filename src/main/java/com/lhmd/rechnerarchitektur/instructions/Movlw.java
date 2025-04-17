package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * The eight bit literal ’k’ is loaded into W register.
 */
public class Movlw extends Instruction {
    private final int literal;

    public Movlw(int instruction) {
        super(instruction);

        literal = IntUtils.bitRange(instruction, 0, 7);
    }

    @Override
    public void execute(ExecutionParams params) {
        setW(params, literal);
    }
}
