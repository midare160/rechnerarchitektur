package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.memory.ProgramMemory;
import com.lhmd.rechnerarchitektur.values.IntBox;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProgramCounterTest {
    private final IntBox pclRegister;
    private final IntBox pclathRegister;
    private final ProgramCounter programCounter;

    @Autowired
    public ProgramCounterTest(DataMemory dataMemory, ProgramCounter programCounter) {
        this.pclRegister = dataMemory.getRegister(0x02);
        this.pclathRegister = dataMemory.getRegister(0x0A);
        this.programCounter = programCounter;
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

        for (var i = 257; i < ProgramCounter.MAX_SIZE; i++) {
            programCounter.increment();
        }

        assertEquals(ProgramCounter.MAX_SIZE - 1, programCounter.get());
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
