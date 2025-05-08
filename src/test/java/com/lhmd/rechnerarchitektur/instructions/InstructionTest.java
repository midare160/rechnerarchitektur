package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.computing.Alu;
import com.lhmd.rechnerarchitektur.memory.*;

public abstract class InstructionTest {
    protected ExecutionContext getExecutionParams() {
        var dataMemory = new DataMemory();

        return new ExecutionContext(dataMemory, new ProgramStack(), new Alu(dataMemory.status()));
    }
}
