package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.memory.WRegister;

public class Xorlw extends Instruction {
    private final int literal;

    public Xorlw(int instruction) {
        super(instruction);

        literal = IntUtils.bitRange(instruction, 0, 7);
    }

    @Override
    public void execute() {
        var wRegister = WRegister.instance();
        var result = wRegister.get() ^ literal;

        wRegister.set(result);
        DataMemory.instance().status().setZ(result == 0);
    }
}
