package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * Call Subroutine. First, return address (PC+1) is pushed onto the stack.
 * The eleven bit immediate address is loaded into PC bits <10:0>.
 * The upper bits of the PC are loaded from PCLATH.
 * CALL is a two cycle instruction.
 */
public class Call extends InstructionBase {
    private final int address;

    public Call(int instruction) {
        super(instruction);

        address = IntUtils.bitRange(instruction, 0, 10);
    }

    @Override
    public void execute(ExecutionContext context) {
        // TODO check if +1 correct
        var stackValue = context.dataMemory().programCounter().get();
        context.programStack().push(stackValue);

        context.dataMemory().programCounter().fromJump(address);
    }

    @Override
    public boolean isTwoCycle() {
        return true;
    }
}
