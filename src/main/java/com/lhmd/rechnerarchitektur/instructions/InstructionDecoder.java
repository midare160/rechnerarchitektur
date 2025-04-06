package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

import java.util.*;
import java.util.function.Function;

public class InstructionDecoder {

    private static final Map<Integer, Function<Integer, ? extends Instruction>> OPCODES;

    static {
        OPCODES = new LinkedHashMap<>();
        OPCODES.put(0b11_1110_0000_0000, Addlw::new);
        OPCODES.put(0b11_1001_0000_0000, Andlw::new);
        OPCODES.put(0b11_1100_0000_0000, Sublw::new);
        OPCODES.put(0b11_1010_0000_0000, Xorlw::new);
        OPCODES.put(0b11_1000_0000_0000, Iorlw::new);
        OPCODES.put(0b11_0100_0000_0000, Retlw::new);
        OPCODES.put(0b11_0000_0000_0000, Movlw::new);
        OPCODES.put(0b10_1000_0000_0000, Goto::new);
        OPCODES.put(0b10_0000_0000_0000, Call::new);
        OPCODES.put(0b00_0000_0000_1000, Return::new);
        OPCODES.put(0b00_0000_0000_0000, Nop::new);
    }

    public static Instruction decode(String instruction) throws ReflectiveOperationException {
        var hexInstruction = IntUtils.tryParse(instruction, 16);

        if (hexInstruction == null) {
            return null;
        }

        for (var kvp : OPCODES.entrySet()) {
            if ((hexInstruction & kvp.getKey()) != kvp.getKey()) {
                continue;
            }

            return kvp.getValue().apply(hexInstruction);
        }

        throw new NoSuchMethodException("No instruction found for " + instruction);
    }
}
