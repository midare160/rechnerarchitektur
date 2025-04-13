package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

public class Iorlw extends Instruction {
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
