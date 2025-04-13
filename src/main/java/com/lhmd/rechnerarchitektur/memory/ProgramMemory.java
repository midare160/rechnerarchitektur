package com.lhmd.rechnerarchitektur.memory;

import com.lhmd.rechnerarchitektur.instructions.Instruction;

import java.util.*;

public class ProgramMemory {
    public static final int MAX_SIZE = 1024;

    private final Instruction[] instructions;

    public ProgramMemory(Instruction[] instructions) {
        if (instructions.length > MAX_SIZE) {
            throw new IllegalArgumentException("Instruction array may only be " + MAX_SIZE + " elements large");
        }

        this.instructions = instructions;
    }

    public List<Instruction> getInstructions() {
        return List.of(instructions);
    }

    public Instruction get(int index) {
        return instructions[index % MAX_SIZE];
    }

    public void reset() {
        Arrays.fill(instructions, null);
    }
}
