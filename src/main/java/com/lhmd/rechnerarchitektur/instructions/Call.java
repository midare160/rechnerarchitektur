package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.ProgramStack;
import com.lhmd.rechnerarchitektur.registers.ProgramCounter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Call Subroutine. First, return address (PC+1) is pushed onto the stack.
 * The eleven bit immediate address is loaded into PC bits <10:0>.
 * The upper bits of the PC are loaded from PCLATH.
 * CALL is a two cycle instruction.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Call extends InstructionBase {
    private final ProgramStack programStack;
    private final ProgramCounter programCounter;

    private int address;

    public Call(ProgramStack programStack, ProgramCounter programCounter) {
        this.programStack = programStack;
        this.programCounter = programCounter;
    }

    @Override
    public void execute() {
        programStack.push(programCounter.get());
        programCounter.fromJump(address);
    }

    @Override
    public boolean isTwoCycle() {
        return true;
    }

    @Override
    protected void onInitialized() {
        address = IntUtils.bitRange(getInstruction(), 0, 10);
    }
}
