package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.registers.ProgramCounter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * GOTO is an unconditional branch.
 * The eleven bit immediate value is loaded into PC bits <10:0>.
 * The upper bits of PC are loaded from PCLATH<4:3>.
 * GOTO is a two cycle instruction.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Goto extends InstructionBase {
    private final ProgramCounter programCounter;

    private int address;

    public Goto(ProgramCounter programCounter) {
        this.programCounter = programCounter;
    }

    @Override
    public void execute() {
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
