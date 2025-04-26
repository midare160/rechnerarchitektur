package com.lhmd.rechnerarchitektur.instructions;

/**
 * Return from subroutine.
 * The stack is POPed and the top of the stack (TOS) is loaded into the program counter.
 * This is a two cycle instruction.
 */
public class Return extends InstructionBase {
    public Return(int instruction) {
        super(instruction);
    }

    @Override
    public void execute(ExecutionParams params) {
        popStack(params);
    }

    @Override
    public boolean isTwoCycle() {
        return true;
    }
}
