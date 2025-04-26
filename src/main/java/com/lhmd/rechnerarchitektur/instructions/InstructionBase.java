package com.lhmd.rechnerarchitektur.instructions;

public abstract class InstructionBase {
    private final int instruction;

    public InstructionBase(int instruction) {
        if (instruction > 0b11_1111_1111_1111) {
            throw new IllegalArgumentException("Instruction may only be 14 bits wide. Parameter was " + instruction);
        }

        this.instruction = instruction;
    }

    public int getInstruction() {
        return instruction;
    }

    public abstract void execute(ExecutionParams params);

    public boolean isTwoCycle() {
        return false;
    }

    protected int getW(ExecutionParams params) {
        return params.dataMemory().W().get();
    }

    protected void setW(ExecutionParams params, int w) {
        params.dataMemory().W().set(w);
    }

    protected void popStack(ExecutionParams params) {
        var stackAddress = params.programStack().pop();
        params.dataMemory().programCounter().override(stackAddress);
    }

    protected void updateC_Add(ExecutionParams params, int a, int b) {
        params.dataMemory().status().setC(a + b > 255);
    }

    protected void updateC_Sub(ExecutionParams params, int a, int b) {
        params.dataMemory().status().setC(b >= a);
    }

    protected void updateDC_Add(ExecutionParams params, int a, int b) {
        // Mask to get lower 4 bits (nibble)
        var lowerNibbleSum = (a & 0x0F) + (b & 0x0F);
        var carryOccured = lowerNibbleSum > 0x0F;

        // DC = true if carry from bit 3
        params.dataMemory().status().setDC(carryOccured);
    }

    protected void updateDC_Sub(ExecutionParams params, int a, int b) {
        // For subtraction, polarity is reversed
        var noBorrowOccured = (a & 0x0F) >= (b & 0x0F);

        // DC = true if no borrow from bit 3
        params.dataMemory().status().setDC(noBorrowOccured);
    }

    protected void updateZ(ExecutionParams params, int result) {
        params.dataMemory().status().setZ(result == 0);
    }
}
