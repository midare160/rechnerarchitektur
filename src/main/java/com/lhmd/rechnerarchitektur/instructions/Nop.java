package com.lhmd.rechnerarchitektur.instructions;

/**
 * No operation.
 */
public class Nop extends Instruction {
    public static final Nop DEFAULT = new Nop(0x00);

    public Nop(int instruction) {
        super(instruction);
    }

    @Override
    public void execute(ExecutionParams params) {
        // Does nothing
    }
}
