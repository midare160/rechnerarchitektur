package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.memory.ProgramStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddlwTest extends InstructionTest {

    @Test
    public void execute_noOverflow() {
        var params = getExecutionParams();

        params.dataMemory().W().set(0x10);
        new Addlw(0x15).execute(params);

        assertEquals(0x25, params.dataMemory().W().get());

        assertFalse(params.dataMemory().status().getC());
        assertFalse(params.dataMemory().status().getDC());
        assertFalse(params.dataMemory().status().getZ());
    }

    @Test
    public void execute_overflows() {
        var params = getExecutionParams();

        params.dataMemory().W().set(0xF0);
        new Addlw(0x12).execute(params);

        assertEquals(0x02, params.dataMemory().W().get());

        assertTrue(params.dataMemory().status().getC());
        assertFalse(params.dataMemory().status().getDC());
        assertFalse(params.dataMemory().status().getZ());
    }

    @Test
    public void execute_overflowsToZero() {
        var params = getExecutionParams();

        params.dataMemory().W().set(0xFF);
        new Addlw(0x01).execute(params);

        assertEquals(0x00, params.dataMemory().W().get());

        assertTrue(params.dataMemory().status().getC());
        assertTrue(params.dataMemory().status().getDC());
        assertTrue(params.dataMemory().status().getZ());
    }
}
