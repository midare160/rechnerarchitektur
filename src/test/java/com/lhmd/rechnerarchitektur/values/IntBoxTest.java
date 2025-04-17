package com.lhmd.rechnerarchitektur.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntBoxTest {

    @Test
    public void constructor_initialValueEqualsZero() {
        var box = new IntBox();

        assertEquals(0, box.get());
    }

    @Test
    public void setValue_Null_ThrowsNullPointerException() {
        var box = new IntBox();

        assertThrows(NullPointerException.class, () -> box.setValue(null));
    }
}
