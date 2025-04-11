package com.lhmd.rechnerarchitektur.memory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramStackTest {

    @Test
    public void push() {
        var stack = ProgramStack.instance();

        assertEquals(0, stack.getPointer());

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.getPointer());

        assertEquals(3, stack.pop());
        assertEquals(2, stack.getPointer());

        assertEquals(2, stack.pop());
        assertEquals(1, stack.getPointer());

        assertEquals(1, stack.pop());
        assertEquals(0, stack.getPointer());
    }

    @Test
    public void push_overflow() {
        var stack = ProgramStack.instance();

        // After the stack has been pushed eight times, the ninth push over-
        // writes the value that was stored from the first push. The
        // tenth push overwrites the second push (and so on).
        for (int i = 1; i <= 10; i++) {
            stack.push(i);
        }

        assertEquals(2, stack.getPointer());
        assertEquals(10, stack.pop());

        assertEquals(1, stack.getPointer());
        assertEquals(9, stack.pop());

        assertEquals(0, stack.getPointer());
        assertEquals(8, stack.pop());
    }

    @Test
    public void pop_underflow(){
        var stack = ProgramStack.instance();

        for (int i = 1; i <= 8; i++) {
            stack.push(i);
        }

        var firstPop = stack.pop();

        // If the stack is effectively popped nine times, the stackpointer
        // value is the same as the value from the first pop.
        for (int i = 0; i < 7; i++) {
            stack.pop();
        }

        assertEquals(firstPop, stack.pop());
    }
}
