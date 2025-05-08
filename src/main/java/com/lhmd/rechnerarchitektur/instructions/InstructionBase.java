package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.ProgramMemory;

public abstract class InstructionBase {
    private final int instruction;

    public InstructionBase(int instruction) {
        if (instruction > ProgramMemory.INSTRUCTION_SIZE || instruction < 0) {
            throw new IllegalArgumentException("Instruction may only be %d bits wide (%dd). Parameter was %d".formatted(
                    ProgramMemory.INSTRUCTION_WIDTH,
                    ProgramMemory.INSTRUCTION_SIZE,
                    instruction));
        }

        this.instruction = instruction;
    }

    public int getInstruction() {
        return instruction;
    }

    public abstract void execute(ExecutionContext context);

    public boolean isTwoCycle() {
        return false;
    }

    protected int getW(ExecutionContext context) {
        return context.dataMemory().W().get();
    }

    protected void setW(ExecutionContext context, int w) {
        context.dataMemory().W().set(w);
    }

    protected void popStack(ExecutionContext context) {
        var stackAddress = context.programStack().pop();
        context.dataMemory().programCounter().set(stackAddress);
    }
}
