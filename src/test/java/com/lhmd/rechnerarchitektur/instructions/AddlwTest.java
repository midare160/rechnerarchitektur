package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.StatusRegister;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AddlwTest {
    private final WRegister wRegister;
    private final Addlw addlw;
    private final StatusRegister statusRegister;

    @Autowired
    public AddlwTest(StatusRegister statusRegister, WRegister wRegister, Addlw addlw) {
        this.statusRegister = statusRegister;
        this.wRegister = wRegister;
        this.addlw = addlw;
    }

    @Test
    public void execute_noOverflow() {
        wRegister.set(0x10);
        addlw.setInstruction(0x15);

        addlw.execute();

        assertEquals(0x25, wRegister.get());

        assertFalse(statusRegister.getC());
        assertFalse(statusRegister.getDC());
        assertFalse(statusRegister.getZ());
    }

    @Test
    public void execute_overflows() {
        wRegister.set(0xF0);
        addlw.setInstruction(0x12);

        addlw.execute();

        assertEquals(0x02, wRegister.get());

        assertTrue(statusRegister.getC());
        assertFalse(statusRegister.getDC());
        assertFalse(statusRegister.getZ());
    }

    @Test
    public void execute_overflowsToZero() {
        wRegister.set(0xFF);
        addlw.setInstruction(0x01);

        addlw.execute();

        assertEquals(0x00, wRegister.get());

        assertTrue(statusRegister.getC());
        assertTrue(statusRegister.getDC());
        assertTrue(statusRegister.getZ());
    }
}
