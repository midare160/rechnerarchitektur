package com.lhmd.rechnerarchitektur.instructions;

public abstract class Instruction {
    private final int instruction;

    public Instruction(int instruction) {
        if (instruction > 0b11_1111_1111_1111) {
            throw new IllegalArgumentException("Instruction may only be 14 bits wide. Parameter was " + instruction);
        }

        this.instruction = instruction;
    }

    public int getInstruction() {
        return instruction;
    }

    public abstract void execute();
}
