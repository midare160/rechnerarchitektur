package com.lhmd.rechnerarchitektur.parsing;

import com.lhmd.rechnerarchitektur.instructions.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InstructionDecoder {
    private static final Map<Integer, Class<? extends InstructionBase>> OPCODES;

    static {
        var opCodes = new TreeMap<Integer, Class<? extends InstructionBase>>(Comparator.reverseOrder());

        opCodes.put(0b11_1110_0000_0000, Addlw.class);
        opCodes.put(0b11_1001_0000_0000, Andlw.class);
        opCodes.put(0b11_1100_0000_0000, Sublw.class);
        opCodes.put(0b11_1010_0000_0000, Xorlw.class);
        opCodes.put(0b11_1000_0000_0000, Iorlw.class);
        opCodes.put(0b11_0100_0000_0000, Retlw.class);
        opCodes.put(0b11_0000_0000_0000, Movlw.class);
        opCodes.put(0b10_1000_0000_0000, Goto.class);
        opCodes.put(0b10_0000_0000_0000, Call.class);
        opCodes.put(0b01_1100_0000_0000, Btfss.class);
        opCodes.put(0b01_1000_0000_0000, Btfsc.class);
        opCodes.put(0b01_0100_0000_0000, Bsf.class);
        opCodes.put(0b01_0000_0000_0000, Bcf.class);
        opCodes.put(0b00_1110_0000_0000, Swapf.class);
        opCodes.put(0b00_1111_0000_0000, Incfsz.class);
        opCodes.put(0b00_1101_0000_0000, Rlf.class);
        opCodes.put(0b00_1100_0000_0000, Rrf.class);
        opCodes.put(0b00_1010_0000_0000, Incf.class);
        opCodes.put(0b00_1011_0000_0000, Decfsz.class);
        opCodes.put(0b00_1001_0000_0000, Comf.class);
        opCodes.put(0b00_1000_0000_0000, Movf.class);
        opCodes.put(0b00_0111_0000_0000, Addwf.class);
        opCodes.put(0b00_0110_0000_0000, Xorwf.class);
        opCodes.put(0b00_0100_0000_0000, Iorwf.class);
        opCodes.put(0b00_0101_0000_0000, Andwf.class);
        opCodes.put(0b00_0011_0000_0000, Decf.class);
        opCodes.put(0b00_0010_0000_0000, Subwf.class);
        opCodes.put(0b00_0001_1000_0000, Clrf.class);
        opCodes.put(0b00_0001_0000_0000, Clrw.class);
        opCodes.put(0b00_0000_1000_0000, Movwf.class);
        opCodes.put(0b00_0000_0000_1001, Retfie.class);
        opCodes.put(0b00_0000_0000_1000, Return.class);
        opCodes.put(0b00_0000_0000_0000, Nop.class);

        OPCODES = Collections.unmodifiableMap(opCodes);
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
