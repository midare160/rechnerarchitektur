package com.lhmd.rechnerarchitektur.instructions;

public class Nop extends Instruction {
    public Nop(int instruction) {
        super(instruction);
    }

    @Override
    public void execute() {
        // Does nothing
    }
}
