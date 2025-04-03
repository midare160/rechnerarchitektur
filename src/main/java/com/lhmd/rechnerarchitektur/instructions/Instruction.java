package com.lhmd.rechnerarchitektur.instructions;

public abstract class Instruction {
    private final int instruction;

    public Instruction(int instruction) {
        this.instruction = instruction;
    }

    public int getInstruction() {
        return instruction;
    }

    public abstract void execute();
}
