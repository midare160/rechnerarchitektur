package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * The W register is loaded with the eight bit literal ’k’.
 * The program counter is loaded from the top of the stack (the return address).
 * This is a two cycle instruction.
 */
public class Retlw extends InstructionBase {
    private final int literal;

    public Retlw(int instruction) {
        super(instruction);

        literal = IntUtils.bitRange(instruction, 0, 7);
    }

    @Override
    public void execute(ExecutionContext context) {
        setW(context, literal);
        popStack(context);
    }

    @Override
    public boolean isTwoCycle() {
        return true;
    }
}
