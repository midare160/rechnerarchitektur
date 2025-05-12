package com.lhmd.rechnerarchitektur.parsing;

import com.lhmd.rechnerarchitektur.instructions.*;

import java.util.*;
import java.util.function.Function;

public class InstructionDecoder {
    private static final Map<Integer, Function<Integer, ? extends InstructionBase>> OPCODES;

    static {
        OPCODES = new TreeMap<>(Comparator.reverseOrder());
        OPCODES.put(0b11_1110_0000_0000, Addlw::new);
        OPCODES.put(0b11_1001_0000_0000, Andlw::new);
        OPCODES.put(0b11_1100_0000_0000, Sublw::new);
        OPCODES.put(0b11_1010_0000_0000, Xorlw::new);
        OPCODES.put(0b11_1000_0000_0000, Iorlw::new);
        OPCODES.put(0b11_0100_0000_0000, Retlw::new);
        OPCODES.put(0b11_0000_0000_0000, Movlw::new);
        OPCODES.put(0b10_1000_0000_0000, Goto::new);
        OPCODES.put(0b10_0000_0000_0000, Call::new);
        OPCODES.put(0b00_1110_0000_0000, Swapf::new);
        OPCODES.put(0b00_1010_0000_0000, Incf::new);
        OPCODES.put(0b00_1001_0000_0000, Comf::new);
        OPCODES.put(0b00_1000_0000_0000, Movf::new);
        OPCODES.put(0b00_0111_0000_0000, Addwf::new);
        OPCODES.put(0b00_0110_0000_0000, Xorwf::new);
        OPCODES.put(0b00_0100_0000_0000, Iorwf::new);
        OPCODES.put(0b00_0101_0000_0000, Andwf::new);
        OPCODES.put(0b00_0011_0000_0000, Decf::new);
        OPCODES.put(0b00_0010_0000_0000, Subwf::new);
        OPCODES.put(0b00_0001_1000_0000, Clrf::new);
        OPCODES.put(0b00_0001_0000_0000, Clrw::new);
        OPCODES.put(0b00_0000_1000_0000, Movwf::new);
        OPCODES.put(0b00_0000_0000_1000, Return::new);
        OPCODES.put(0b00_0000_0000_0000, Nop::new);
    }

    public static InstructionBase decode(int instruction) {
        for (var entry : OPCODES.entrySet()) {
            if ((instruction & entry.getKey()) != entry.getKey()) {
                continue;
            }

            return entry.getValue().apply(instruction);
        }

        throw new IllegalArgumentException("No instruction found for " + instruction);
    }
}
