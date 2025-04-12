package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.memory.WRegister;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddlwTest extends InstructionTestBase {

    @Test
    public void execute_noOverflow() {
        WRegister.instance().set(0x10);
        new Addlw(0x15).execute();

        assertEquals(0x25, WRegister.instance().get());

        assertFalse(DataMemory.instance().status().getC());
        assertFalse(DataMemory.instance().status().getDC());
        assertFalse(DataMemory.instance().status().getZ());
    }

    @Test
    public void execute_overflows() {
        WRegister.instance().set(0xF0);
        new Addlw(0x12).execute();

        assertEquals(0x02, WRegister.instance().get());

        assertTrue(DataMemory.instance().status().getC());
        assertFalse(DataMemory.instance().status().getDC());
        assertFalse(DataMemory.instance().status().getZ());
    }

    @Test
    public void execute_overflowsToZero() {
        WRegister.instance().set(0xFF);
        new Addlw(0x01).execute();

        assertEquals(0x00, WRegister.instance().get());

        assertTrue(DataMemory.instance().status().getC());
        assertTrue(DataMemory.instance().status().getDC());
        assertTrue(DataMemory.instance().status().getZ());
    }
}
