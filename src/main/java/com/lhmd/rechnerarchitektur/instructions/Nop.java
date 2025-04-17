package com.lhmd.rechnerarchitektur.instructions;

/**
 * No operation.
 */
public class Nop extends Instruction {
    public Nop(int instruction) {
        super(instruction);
    }

    @Override
    public void execute(ExecutionParams params) {
        // Does nothing
    }
}
