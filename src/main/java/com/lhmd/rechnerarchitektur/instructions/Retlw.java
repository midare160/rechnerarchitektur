package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.memory.ProgramStack;
import com.lhmd.rechnerarchitektur.registers.ProgramCounter;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The W register is loaded with the eight bit literal ’k’.
 * The program counter is loaded from the top of the stack (the return address).
 * This is a two cycle instruction.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Retlw extends InstructionBase {
    private final ProgramStack programStack;
    private final ProgramCounter programCounter;
    private final WRegister wRegister;

    private int literal;

    public Retlw(ProgramStack programStack, ProgramCounter programCounter, WRegister wRegister) {
        this.programStack = programStack;
        this.programCounter = programCounter;
        this.wRegister = wRegister;
    }

    @Override
    public void execute() {
        wRegister.set(literal);
        programCounter.set(programStack.pop());
    }

    @Override
    public boolean isTwoCycle() {
        return true;
    }

    @Override
    protected void onInitialized() {
        literal = IntUtils.bitRange(getInstruction(), 0, 7);
    }
}
