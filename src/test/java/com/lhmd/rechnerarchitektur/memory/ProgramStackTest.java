package com.lhmd.rechnerarchitektur.memory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProgramStackTest {
    private final ProgramStack programStack;

    @Autowired
    public ProgramStackTest(ProgramStack programStack) {
        this.programStack = programStack;
    }

    @Test
    public void push_threeValues() {
        assertEquals(0, programStack.pointer().get());

        programStack.push(1);
        programStack.push(2);
        programStack.push(3);

        assertEquals(3, programStack.pointer().get());
    }

    @Test
    public void pop_threeValues() {
        programStack.push(1);
        programStack.push(2);
        programStack.push(3);

        assertEquals(3, programStack.pop());
        assertEquals(2, programStack.pointer().get());

        assertEquals(2, programStack.pop());
        assertEquals(1, programStack.pointer().get());

        assertEquals(1, programStack.pop());
        assertEquals(0, programStack.pointer().get());
    }

    @Test
    public void push_overflow() {
        // After the stack has been pushed eight times, the ninth push over-
        // writes the value that was stored from the first push. The
        // tenth push overwrites the second push (and so on).
        for (int i = 1; i <= 10; i++) {
            programStack.push(i);
        }

        assertEquals(2, programStack.pointer().get());

        assertEquals(10, programStack.pop());
        assertEquals(1, programStack.pointer().get());

        assertEquals(9, programStack.pop());
        assertEquals(0, programStack.pointer().get());
    }

    @Test
    public void pop_underflow(){
        for (int i = 1; i <= 7; i++) {
            programStack.push(i);
        }

        assertEquals(7, programStack.pointer().get());

        var firstPop = programStack.pop();
        assertEquals(6, programStack.pointer().get());

        // If the stack is effectively popped nine times, the stackpointer
        // value is the same as the value from the first pop.
        for (int i = 0; i < 7; i++) {
            programStack.pop();
        }

        assertEquals(7, programStack.pointer().get());

        assertEquals(firstPop, programStack.pop());
        assertEquals(6, programStack.pointer().get());
    }

    @Test
    public void peek_returnsCorrectValue() {
        programStack.push(1);
        programStack.push(2);

        assertEquals(2, programStack.peek());
    }

    @Test
    public void peek_underflow() {
        for (var i = 1; i <= ProgramStack.MAX_SIZE; i++) {
            programStack.push(i);
        }

        assertEquals(8, programStack.peek());
    }
}
