package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.memory.*;

public class ALU {
    private final ProgramMemory programMemory;
    private final ProgramStack programStack;
    private final DataMemory dataMemory;

    public ALU(ProgramMemory programMemory, ProgramStack programStack, DataMemory dataMemory) {
        this.programMemory = programMemory;
        this.programStack = programStack;
        this.dataMemory = dataMemory;
    }

    public void reset() {
        programMemory.reset();
        programStack.reset();
        dataMemory.reset();
    }

    // On Program Counter and instruction cycle: Page 10 in docs
}
