package com.lhmd.rechnerarchitektur.memory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramStackTest {

    @Test
    public void push() {
        var stack = ProgramStack.instance();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    public void push_overflow() {
        var stack = ProgramStack.instance();

        for (int i = 1; i <= 10; i++) {
            stack.push(i);
        }

        assertEquals(2, stack.getPointer());
        assertEquals(10, stack.pop());

        assertEquals(1, stack.getPointer());
        assertEquals(9, stack.pop());

        assertEquals(0, stack.getPointer());
        assertEquals(8, stack.pop());

        assertEquals(7, stack.getPointer());
        assertEquals(7, stack.pop());
    }
}
