package com.lhmd.rechnerarchitektur.parsing;

import com.lhmd.rechnerarchitektur.instructions.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
public class InstructionDecoder {
    private static final Map<Integer, Class<? extends InstructionBase>> OPCODES;

    static {
        OPCODES = new TreeMap<>(Comparator.reverseOrder());
        OPCODES.put(0b11_1110_0000_0000, Addlw.class);
        OPCODES.put(0b11_1001_0000_0000, Andlw.class);
        OPCODES.put(0b11_1100_0000_0000, Sublw.class);
        OPCODES.put(0b11_1010_0000_0000, Xorlw.class);
        OPCODES.put(0b11_1000_0000_0000, Iorlw.class);
        OPCODES.put(0b11_0100_0000_0000, Retlw.class);
        OPCODES.put(0b11_0000_0000_0000, Movlw.class);
        OPCODES.put(0b10_1000_0000_0000, Goto.class);
        OPCODES.put(0b10_0000_0000_0000, Call.class);
        OPCODES.put(0b00_1110_0000_0000, Swapf.class);
        OPCODES.put(0b00_1010_0000_0000, Incf.class);
        OPCODES.put(0b00_1001_0000_0000, Comf.class);
        OPCODES.put(0b00_1000_0000_0000, Movf.class);
        OPCODES.put(0b00_0111_0000_0000, Addwf.class);
        OPCODES.put(0b00_0110_0000_0000, Xorwf.class);
        OPCODES.put(0b00_0100_0000_0000, Iorwf.class);
        OPCODES.put(0b00_0101_0000_0000, Andwf.class);
        OPCODES.put(0b00_0011_0000_0000, Decf.class);
        OPCODES.put(0b00_0010_0000_0000, Subwf.class);
        OPCODES.put(0b00_0001_1000_0000, Clrf.class);
        OPCODES.put(0b00_0001_0000_0000, Clrw.class);
        OPCODES.put(0b00_0000_1000_0000, Movwf.class);
        OPCODES.put(0b00_0000_0000_1000, Return.class);
        OPCODES.put(0b00_0000_0000_0000, Nop.class);
    }

    private final BeanFactory beanFactory;

    public InstructionDecoder(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public InstructionBase decode(int instruction) {
        for (var entry : OPCODES.entrySet()) {
            if ((instruction & entry.getKey()) != entry.getKey()) {
                continue;
            }

            var instance = beanFactory.getBean(entry.getValue());
            instance.setInstruction(instruction);

            return instance;
        }

        throw new IllegalArgumentException("No instruction found for " + instruction);
    }
}
