package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.*;
import org.junit.jupiter.api.BeforeEach;

public abstract class InstructionTestBase {
    @BeforeEach
    public void setUp() {
        ProgramMemory.reset();
        ProgramStack.reset();
        DataMemory.reset();
        WRegister.reset();
    }
}
