package com.lhmd.rechnerarchitektur.memory;

public class ProgramStack {
    private static final int MAX_SIZE = 8;

    private static ProgramStack instance = new ProgramStack();

    public static ProgramStack instance() {
        return instance;
    }

    public static void reset() {
        instance = new ProgramStack();
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
        pointer = Math.floorMod(pointer - 1, MAX_SIZE);

        return stack[pointer];
    }
}
