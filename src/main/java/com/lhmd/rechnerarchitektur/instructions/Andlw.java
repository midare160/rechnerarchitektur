package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.registers.StatusRegister;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import com.lhmd.rechnerarchitektur.common.IntUtils;

public class Andlw extends Instruction {
    private final int literal;

    public Andlw(int instruction) {
        super(instruction);

        literal = IntUtils.bitRange(instruction, 0, 7);
    }

    @Override
    public void execute() {
        var wRegister = WRegister.instance();
        var result = wRegister.get() & literal;

        wRegister.set(result);
        StatusRegister.instance().Z().set(result == 0);
    }
}
