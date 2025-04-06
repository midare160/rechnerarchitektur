package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.registers.StatusRegister;
import com.lhmd.rechnerarchitektur.registers.WRegister;

public class Sublw extends Instruction {
    private final int literal;

    public Sublw(int instruction) {
        super(instruction);

        literal = IntUtils.bitRange(instruction, 0, 7);
    }

    @Override
    public void execute() {
        var wRegister = WRegister.instance();
        var statusRegister = StatusRegister.instance();
        var result = (literal - wRegister.get()) % 256;

        statusRegister.C().set(literal >= wRegister.get());
        wRegister.set(result);
        statusRegister.Z().set(result == 0);

        // TODO set DC flag
    }
}
