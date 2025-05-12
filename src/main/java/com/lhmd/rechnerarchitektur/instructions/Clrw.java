package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

/**
 * W register is cleared. Zero bit (Z) is set.
 */
public class Clrw extends InstructionBase {
    public Clrw(int instruction) {
        super(instruction);
    }

    @Override
    public void execute(ExecutionContext context) {
        setW(context, 0);
        context.dataMemory().status().setZ(true);
    }
}
