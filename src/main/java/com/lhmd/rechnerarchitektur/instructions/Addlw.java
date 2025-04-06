package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.registers.StatusRegister;
import com.lhmd.rechnerarchitektur.registers.WRegister;

public class Addlw extends Instruction {
    private final int literal;

    public Addlw(int instruction) {
        super(instruction);

        literal = IntUtils.bitRange(instruction, 0, 7);
    }

    @Override
    public void execute() {
        var wRegister = WRegister.instance();
        var statusRegister = StatusRegister.instance();
        var result = (wRegister.get() + literal) % 256;

        statusRegister.C().set(wRegister.get() + literal > 255);
        wRegister.set(result);
        statusRegister.Z().set(result == 0);

        // TODO set DC flag
    }
}
