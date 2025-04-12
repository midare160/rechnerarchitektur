package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.memory.WRegister;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AndlwTest extends InstructionTestBase {
    @Test
    public void execute() {
        WRegister.instance().set(0xA3);
        new Andlw(0x5F).execute();

        assertEquals(0x03, WRegister.instance().get());
        assertFalse(DataMemory.instance().status().getZ());
    }

    @Test
    public void execute_equalsZero() {
        WRegister.instance().set(0xA0);
        new Andlw(0x50).execute();

        assertEquals(0x00, WRegister.instance().get());
        assertTrue(DataMemory.instance().status().getZ());
    }
}
