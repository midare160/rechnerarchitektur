package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.junit.jupiter.api.BeforeEach;

public abstract class InstructionTestBase {
    @BeforeEach
    public void setUp() {
        DataMemory.reset();
        WRegister.reset();
    }
}
