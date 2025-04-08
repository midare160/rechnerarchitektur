package com.lhmd.rechnerarchitektur.memory;

import com.lhmd.rechnerarchitektur.instructions.Instruction;

import java.util.*;

public class ProgramMemory {
    private static final int MAX_SIZE = 1024;

    private static ProgramMemory instance = new ProgramMemory();

    public static ProgramMemory instance() {
        return instance;
    }

    public static void reset() {
        instance = new ProgramMemory();
    }

    private final Instruction[] instructions;

    private ProgramMemory() {
        instructions = new Instruction[MAX_SIZE];
    }

    public List<Instruction> getInstructions() {
        return Arrays.stream(instructions)
                .filter(Objects::nonNull)
                .toList();
    }

    public Instruction get(int index) {
        return instructions[index % MAX_SIZE];
    }

    public void set(int index, Instruction instruction) {
        instructions[index % MAX_SIZE] = instruction;
    }
}
