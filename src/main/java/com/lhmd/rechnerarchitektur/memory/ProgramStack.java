package com.lhmd.rechnerarchitektur.memory;

import java.util.Arrays;

public class ProgramStack {
    public static final int MAX_SIZE = 8;

    private final int[] stack;

    private int pointer;

    public ProgramStack() {
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

    public int peek() {
        return stack[Math.floorMod(pointer - 1, MAX_SIZE)];
    }

    public void reset() {
        pointer = 0;
        Arrays.fill(stack, 0);
    }
}
