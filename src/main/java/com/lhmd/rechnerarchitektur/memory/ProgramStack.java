package com.lhmd.rechnerarchitektur.memory;

import java.util.Stack;

public class ProgramStack {
    private static final int MAX_SIZE = 8;

    private static ProgramStack instance = new ProgramStack();

    public static ProgramStack instance() {
        return instance;
    }

    public static void reset() {
        instance = new ProgramStack();
    }

    private final Stack<Integer> stack;

    private ProgramStack() {
        stack = new Stack<>();
    }

    public void push(int value) {
        // TODO MAX_SIZE
        stack.push(value);
    }

    public int pop() {
        // TODO MAX_SIZE
        return stack.pop();
    }
}
