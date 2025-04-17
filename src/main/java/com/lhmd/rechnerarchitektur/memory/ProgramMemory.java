package com.lhmd.rechnerarchitektur.memory;

import com.lhmd.rechnerarchitektur.instructions.*;

import java.util.*;

public class ProgramMemory {
    public static final int MAX_SIZE = 1024;

    private final Instruction[] instructions;

    /**
     * Initializes this instance with the passed {@code instructionMap}.
     *
     * @param instructionMap a {@code Map} which represents the memory address as key and the instruction as value
     */
    public ProgramMemory(Map<Integer, Instruction> instructionMap) {
        if (instructionMap.size() > MAX_SIZE) {
            throw new IllegalArgumentException("Instructions collection may only be " + MAX_SIZE + " elements big");
        }

        instructions = new Instruction[MAX_SIZE];

        for (var i = 0; i < instructions.length; i++) {
            instructions[i] = instructionMap.getOrDefault(i, Nop.DEFAULT);
        }
    }

    public Instruction get(int address) {
        // Accessing a location above the physically implemented address will cause a wrap-around.
        // For example, for the locations 20h, 420h, 820h, C20h, 1020h, 1420h, 1820h, and 1C20h
        // will be the same instruction.
        return instructions[address % MAX_SIZE];
    }

    public void reset() {
        Arrays.fill(instructions, Nop.DEFAULT);
    }
}
