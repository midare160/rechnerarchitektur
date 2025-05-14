package com.lhmd.rechnerarchitektur.instructions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AndlwTest extends InstructionTest {
    @Test
    public void execute() {
        var params = getExecutionParams();

        params.dataMemory().W().set(0xA3);
        new Andlw(0x5F).execute();

        assertEquals(0x03, params.dataMemory().W().get());
        assertFalse(params.dataMemory().status().getZ());
    }

    @Test
    public void execute_equalsZero() {
        var params = getExecutionParams();

        params.dataMemory().W().set(0xA0);
        new Andlw(0x50).execute();

        assertEquals(0x00, params.dataMemory().W().get());
        assertTrue(params.dataMemory().status().getZ());
    }
}
