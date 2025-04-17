package com.lhmd.rechnerarchitektur.parsing;

import com.lhmd.rechnerarchitektur.instructions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InstructionDecoderTest {

    @Test
    public void decode_movlw() {
        var instruction = InstructionDecoder.decode(0x3011);

        assertInstanceOf(Movlw.class, instruction);
    }

    @Test
    public void decode_andlw() {
        var instruction = InstructionDecoder.decode(0x3930);

        assertInstanceOf(Andlw.class, instruction);
    }

    @Test
    public void decode_iorlw() {
        var instruction = InstructionDecoder.decode(0x380D);

        assertInstanceOf(Iorlw.class, instruction);
    }

    @Test
    public void decode_retlw() {
        var instruction = InstructionDecoder.decode(0x3477);

        assertInstanceOf(Retlw.class, instruction);
    }

    @Test
    public void decode_sublw() {
        var instruction = InstructionDecoder.decode(0x3C3D);

        assertInstanceOf(Sublw.class, instruction);
    }

    @Test
    public void decode_xorlw() {
        var instruction = InstructionDecoder.decode(0x3A20);

        assertInstanceOf(Xorlw.class, instruction);
    }

    @Test
    public void decode_addlw() {
        var instruction = InstructionDecoder.decode(0x3E25);

        assertInstanceOf(Addlw.class, instruction);
    }

    @Test
    public void decode_goto() {
        var instruction = InstructionDecoder.decode(0x2806);

        assertInstanceOf(Goto.class, instruction);
    }

    @Test
    public void decode_call() {
        var instruction = InstructionDecoder.decode(0x2006);

        assertInstanceOf(Call.class, instruction);
    }

    @Test
    public void decode_return() {
        var instruction = InstructionDecoder.decode(0x0008);

        assertInstanceOf(Return.class, instruction);
    }

    @Test
    public void decode_nop() {
        var instruction = InstructionDecoder.decode(0x0000);

        assertInstanceOf(Nop.class, instruction);
    }
}
