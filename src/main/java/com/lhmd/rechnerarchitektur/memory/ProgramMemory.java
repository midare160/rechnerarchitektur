package com.lhmd.rechnerarchitektur.memory;

import com.lhmd.rechnerarchitektur.instructions.*;

import java.util.*;

public class ProgramMemory {
    public static final int MAX_SIZE = 1024;
    public static final int INSTRUCTION_WIDTH = 14;
    public static final int INSTRUCTION_MAX_SIZE = (int) Math.pow(2, INSTRUCTION_WIDTH);

    private final List<InstructionBase> instructions;

    /**
     * Initializes this instance with the passed {@code instructionMap}.
     *
     * @param instructionMap a {@code Map} which represents the memory address as key and the instruction as value
     */
    public ProgramMemory(Map<Integer, InstructionBase> instructionMap) {
        if (instructionMap.size() > MAX_SIZE) {
            throw new IllegalArgumentException("Instructions collection may only be " + MAX_SIZE + " elements big");
        }

        this.instructions = createInstructionList(instructionMap);
    }

    public List<InstructionBase> instructions() {
        return instructions;
    }

    public InstructionBase get(int address) {
        // Accessing a location above the physically implemented address will cause a wrap-around.
        // For example, for the locations 20h, 420h, 820h, C20h, 1020h, 1420h, 1820h, and 1C20h
        // will be the same instruction.
        return instructions.get(Math.floorMod(address, MAX_SIZE));
    }

    private List<InstructionBase> createInstructionList(Map<Integer, InstructionBase> instructionMap) {
        var instructionArray = new InstructionBase[MAX_SIZE];

        for (var i = 0; i < instructionArray.length; i++) {
            instructionArray[i] = instructionMap.getOrDefault(i, Nop.DEFAULT);
        }

        return List.of(instructionArray);
    }
}
