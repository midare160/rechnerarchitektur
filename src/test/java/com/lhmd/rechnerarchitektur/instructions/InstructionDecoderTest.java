package com.lhmd.rechnerarchitektur.instructions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InstructionDecoderTest {

    @Test
    public void decode_movlw() {
        var instruction = InstructionDecoder.decode("3011");

        assertInstanceOf(Movlw.class, instruction);
    }

    @Test
    public void decode_andlw() {
        var instruction = InstructionDecoder.decode("3930");

        assertInstanceOf(Andlw.class, instruction);
    }

    @Test
    public void decode_iorlw() {
        var instruction = InstructionDecoder.decode("380D");

        assertInstanceOf(Iorlw.class, instruction);
    }

    @Test
    public void decode_retlw() {
        var instruction = InstructionDecoder.decode("3477");

        assertInstanceOf(Retlw.class, instruction);
    }

    @Test
    public void decode_sublw() {
        var instruction = InstructionDecoder.decode("3C3D");

        assertInstanceOf(Sublw.class, instruction);
    }

    @Test
    public void decode_xorlw() {
        var instruction = InstructionDecoder.decode("3A20");

        assertInstanceOf(Xorlw.class, instruction);
    }

    @Test
    public void decode_addlw() {
        var instruction = InstructionDecoder.decode("3E25");

        assertInstanceOf(Addlw.class, instruction);
    }

    @Test
    public void decode_goto() {
        var instruction = InstructionDecoder.decode("2806");

        assertInstanceOf(Goto.class, instruction);
    }

    @Test
    public void decode_call() {
        var instruction = InstructionDecoder.decode("2006");

        assertInstanceOf(Call.class, instruction);
    }

    @Test
    public void decode_return() {
        var instruction = InstructionDecoder.decode("0008");

        assertInstanceOf(Return.class, instruction);
    }

    @Test
    public void decode_nop() {
        var instruction = InstructionDecoder.decode("0000");

        assertInstanceOf(Nop.class, instruction);
    }
}
