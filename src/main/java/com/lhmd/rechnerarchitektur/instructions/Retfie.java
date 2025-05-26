package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.ProgramStack;
import com.lhmd.rechnerarchitektur.registers.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Return from Interrupt.
 * Stack is POPed and Top of Stack (TOS) is loaded in the PC.
 * Interrupts are enabled by setting Global Interrupt Enable bit, GIE (INTCON<7>).
 * This is a two cycle instruction.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Retfie extends InstructionBase {
    private final ProgramStack programStack;
    private final ProgramCounter programCounter;
    private final IntconRegister intconRegister;

    public Retfie(ProgramStack programStack, ProgramCounter programCounter, IntconRegister intconRegister) {
        this.programStack = programStack;
        this.programCounter = programCounter;
        this.intconRegister = intconRegister;
    }

    @Override
    public void execute() {
        programCounter.set(programStack.pop());
        intconRegister.setGIE(true);
    }

    @Override
    public boolean isTwoCycle() {
        return true;
    }
}
