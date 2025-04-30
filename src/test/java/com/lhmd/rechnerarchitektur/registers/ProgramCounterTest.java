package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.memory.ProgramMemory;
import com.lhmd.rechnerarchitektur.values.IntBox;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramCounterTest {
    private IntBox pclRegister;
    private IntBox pclathRegister;
    private ProgramCounter programCounter;

    @BeforeEach
    public void setUp() {
        pclRegister = new IntBox();
        pclathRegister = new IntBox();
        programCounter = new ProgramCounter(pclRegister, pclathRegister);
    }

    @Test
    public void increment_withOutOverflow_getsIncrementedByOne() {
        pclathRegister.set(2);

        assertEquals(0, pclRegister.get());
        assertEquals(0, programCounter.get());

        programCounter.increment();
        assertEquals(1, programCounter.get());
        assertEquals(1, pclRegister.get());

        assertEquals(1, programCounter.get(), "PC should stay unaffected by PCLATH");
        assertEquals(1, pclRegister.get(), "PCL should stay unaffected by PCLATH");
        assertEquals(2, pclathRegister.get(), "PCLATH should stay completely unaffected");
    }

    @Test
    public void increment_withOverflow_wrapsAround() {
        for (var i = 0; i < 255; i++) {
            programCounter.increment();
        }

        assertEquals(255, programCounter.get());
        assertEquals(255, pclRegister.get());

        programCounter.increment();
        assertEquals(256, programCounter.get());
        assertEquals(0, pclRegister.get());

        for (var i = 257; i < ProgramMemory.MAX_SIZE; i++) {
            programCounter.increment();
        }

        assertEquals(ProgramMemory.MAX_SIZE - 1, programCounter.get());
        assertEquals(255, pclRegister.get());

        programCounter.increment();
        assertEquals(0, programCounter.get());
        assertEquals(0, pclRegister.get());
    }

    @Test
    public void fromJump_combinesPclathAndGoto() {
        pclathRegister.set(0b0_1010);
        var jumpInstruction = 0b10_1001_1100_0101;

        programCounter.increment();
        programCounter.fromJump(jumpInstruction);

        assertEquals(0b0_1001_1100_0101, programCounter.get(), "First 2 Bits of PCLATH + Last 11 Bits from GOTO");
        assertEquals(0b1100_0101, pclRegister.get());
        assertEquals(0b0_1010, pclathRegister.get(), "PCLATH should stay unaffected by fromJump()");
    }

    @Test
    public void override_programCounterAndPclGetUpdated() {
        pclathRegister.set(2);

        programCounter.set(0b0_1001_1100_0101);

        assertEquals(0b0_1001_1100_0101, programCounter.get());
        assertEquals(0b1100_0101, pclRegister.get());
        assertEquals(2, pclathRegister.get(), "PCLATH should stay completely unaffected");
    }

    @Test
    public void onPclChanged_combinesPclathAndPcl() {
        // Increment PC a few times to make sure it gets overriden
        programCounter.increment();
        pclathRegister.set(0b1_0011);

        programCounter.increment();
        pclRegister.set(0b0110_1110);

        assertEquals(0b1_0011_0110_1110, programCounter.get());
    }
}
