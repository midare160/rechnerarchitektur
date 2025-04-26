package com.lhmd.rechnerarchitektur.memory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramStackTest {
    @Test
    public void push_threeValues() {
        var stack = new ProgramStack();

        assertEquals(0, stack.pointer());

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pointer());
    }

    @Test
    public void pop_threeValues() {
        var stack = new ProgramStack();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pointer());

        assertEquals(2, stack.pop());
        assertEquals(1, stack.pointer());

        assertEquals(1, stack.pop());
        assertEquals(0, stack.pointer());
    }

    @Test
    public void push_overflow() {
        var stack = new ProgramStack();

        // After the stack has been pushed eight times, the ninth push over-
        // writes the value that was stored from the first push. The
        // tenth push overwrites the second push (and so on).
        for (int i = 1; i <= 10; i++) {
            stack.push(i);
        }

        assertEquals(2, stack.pointer());

        assertEquals(10, stack.pop());
        assertEquals(1, stack.pointer());

        assertEquals(9, stack.pop());
        assertEquals(0, stack.pointer());
    }

    @Test
    public void pop_underflow(){
        var stack = new ProgramStack();

        for (int i = 1; i <= 7; i++) {
            stack.push(i);
        }

        assertEquals(7, stack.pointer());

        var firstPop = stack.pop();
        assertEquals(6, stack.pointer());

        // If the stack is effectively popped nine times, the stackpointer
        // value is the same as the value from the first pop.
        for (int i = 0; i < 7; i++) {
            stack.pop();
        }

        assertEquals(7, stack.pointer());

        assertEquals(firstPop, stack.pop());
        assertEquals(6, stack.pointer());
    }

    @Test
    public void peek_returnsCorrectValue() {
        var stack = new ProgramStack();
        stack.push(1);
        stack.push(2);

        assertEquals(2, stack.peek());
    }

    @Test
    public void peek_underflow() {
        var stack = new ProgramStack();

        for (var i = 1; i <= ProgramStack.MAX_SIZE; i++) {
            stack.push(i);
        }

        assertEquals(8, stack.peek());
    }
}
