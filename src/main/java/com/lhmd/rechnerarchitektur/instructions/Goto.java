package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * GOTO is an unconditional branch.
 * The eleven bit immediate value is loaded into PC bits <10:0>.
 * The upper bits of PC are loaded from PCLATH<4:3>.
 * GOTO is a two cycle instruction.
 */
public class Goto extends InstructionBase {
    private final int address;

    public Goto(int instruction) {
        super(instruction);

        address = IntUtils.bitRange(instruction, 0, 10);
    }

    @Override
    public void execute(ExecutionParams params) {
        params.dataMemory().programCounter().fromJump(address);
    }

    @Override
    public boolean isTwoCycle() {
        return true;
    }
}
