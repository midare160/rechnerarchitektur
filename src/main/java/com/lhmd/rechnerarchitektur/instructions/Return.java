package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.ProgramStack;
import com.lhmd.rechnerarchitektur.registers.ProgramCounter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Return from subroutine.
 * The stack is POPed and the top of the stack (TOS) is loaded into the program counter.
 * This is a two cycle instruction.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Return extends InstructionBase {
    private final ProgramStack programStack;
    private final ProgramCounter programCounter;

    public Return(ProgramStack programStack, ProgramCounter programCounter) {
        this.programStack = programStack;
        this.programCounter = programCounter;
    }

    @Override
    public void execute() {
        programCounter.set(programStack.pop());
    }

    @Override
    public boolean isTwoCycle() {
        return true;
    }
}
