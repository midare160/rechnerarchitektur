package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

public class Goto extends Instruction {
    private final int address;

    public Goto(int instruction) {
        super(instruction);

        address = IntUtils.bitRange(instruction, 0, 10);
    }

    @Override
    public void execute(ExecutionParams params) {
        
    }
}
