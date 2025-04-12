package com.lhmd.rechnerarchitektur.memory;

import java.util.Arrays;

public class ProgramStack {
    private static final int MAX_SIZE = 8;
    private static final ProgramStack INSTANCE = new ProgramStack();

    public static ProgramStack instance() {
        return INSTANCE;
    }

    public static void reset() {
        INSTANCE.pointer = 0;
        Arrays.fill(INSTANCE.stack, 0);
    }

    private final int[] stack;

    private int pointer;

    private ProgramStack() {
        stack = new int[MAX_SIZE];
    }

    public int getPointer() {
        return pointer;
    }

    public void push(int value) {
        stack[pointer] = value;
        pointer = (pointer + 1) % MAX_SIZE;
    }

    public int pop() {
        // Negative numbers behave incorrectly with %
        pointer = Math.floorMod(pointer - 1, MAX_SIZE);

        return stack[pointer];
    }
}
