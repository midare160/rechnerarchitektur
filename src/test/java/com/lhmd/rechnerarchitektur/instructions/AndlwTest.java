package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AndlwTest {
    private final DataMemory dataMemory;
    private final WRegister wRegister;
    private final Andlw andlw;

    @Autowired
    public AndlwTest(DataMemory dataMemory, WRegister wRegister, Andlw andlw) {
        this.dataMemory = dataMemory;
        this.wRegister = wRegister;
        this.andlw = andlw;
    }

    @Test
    public void execute() {
        wRegister.set(0xA3);
        andlw.setInstruction(0x5F);

        andlw.execute();

        assertEquals(0x03, wRegister.get());
        assertFalse(dataMemory.status().getZ());
    }

    @Test
    public void execute_equalsZero() {
        wRegister.set(0xA0);
        andlw.setInstruction(0x50);

        andlw.execute();

        assertEquals(0x00, wRegister.get());
        assertTrue(dataMemory.status().getZ());
    }
}
