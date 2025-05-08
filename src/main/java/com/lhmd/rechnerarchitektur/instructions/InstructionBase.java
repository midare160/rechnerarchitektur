package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.ProgramMemory;

public abstract class InstructionBase {
    public static final int MAX_SIZE = (int) Math.pow(2, ProgramMemory.INSTRUCTION_WIDTH) - 1;

    private final int instruction;

    public InstructionBase(int instruction) {
        if (instruction > MAX_SIZE) {
            throw new IllegalArgumentException("Instruction may only be %d bits wide (%dd). Parameter was %d".formatted(
                    ProgramMemory.INSTRUCTION_WIDTH,
                    MAX_SIZE,
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

    protected void updateC_Add(ExecutionContext context, int a, int b) {
        context.dataMemory().status().setC(a + b > 255);
    }

    protected void updateC_Sub(ExecutionContext context, int a, int b) {
        context.dataMemory().status().setC(b >= a);
    }

    protected void updateDC_Add(ExecutionContext context, int a, int b) {
        // Mask to get lower 4 bits (nibble)
        var lowerNibbleSum = (a & 0x0F) + (b & 0x0F);
        var carryOccured = lowerNibbleSum > 0x0F;

        // DC = true if carry from bit 3
        context.dataMemory().status().setDC(carryOccured);
    }

    protected void updateDC_Sub(ExecutionContext context, int a, int b) {
        // For subtraction, polarity is reversed
        var noBorrowOccured = (a & 0x0F) >= (b & 0x0F);

        // DC = true if no borrow from bit 3
        context.dataMemory().status().setDC(noBorrowOccured);
    }

    protected void updateZ(ExecutionContext context, int result) {
        context.dataMemory().status().setZ(result == 0);
    }
}
