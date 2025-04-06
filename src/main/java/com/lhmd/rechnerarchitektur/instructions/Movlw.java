package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.registers.WRegister;
import com.lhmd.rechnerarchitektur.common.IntUtils;

public class Movlw extends Instruction {
    private final int literal;

    public Movlw(int instruction) {
        super(instruction);

        literal = IntUtils.bitRange(instruction, 0, 7);
    }

    @Override
    public void execute() {
        WRegister.instance().set(literal);
    }
}
