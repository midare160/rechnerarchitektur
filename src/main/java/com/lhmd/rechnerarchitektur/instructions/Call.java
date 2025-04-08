package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.ProgramStack;

public class Call extends Instruction {
    public Call(int instruction) {
        super(instruction);
    }

    @Override
    public void execute() {
        var programCounter = 0x00; // TODO
        ProgramStack.instance().push(programCounter);
    }
}
