package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.*;

public abstract class InstructionTest {
    protected ExecutionContext getExecutionParams() {
        return new ExecutionContext(new DataMemory(), new ProgramStack());
    }
}
