package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.*;

public abstract class InstructionTest {
    protected ExecutionParams getExecutionParams() {
        return new ExecutionParams(new DataMemory(), new ProgramStack());
    }
}
