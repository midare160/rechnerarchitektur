package com.lhmd.rechnerarchitektur.instructions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstructionDecoderTest {

    @Test
    public void decode_movlw() throws ReflectiveOperationException {
        var instruction = InstructionDecoder.decode("3011");

        assertInstanceOf(Movlw.class, instruction);
    }

    @Test
    public void decode_andlw() throws ReflectiveOperationException {
        var instruction = InstructionDecoder.decode("3930");

        assertInstanceOf(Andlw.class, instruction);
    }

    @Test
    public void decode_iorlw() throws ReflectiveOperationException {
        var instruction = InstructionDecoder.decode("380D");

        assertInstanceOf(Iorlw.class, instruction);
    }

    @Test
    public void decode_sublw() throws ReflectiveOperationException {
        var instruction = InstructionDecoder.decode("3C3D");

        assertInstanceOf(Sublw.class, instruction);
    }

    @Test
    public void decode_xorlw() throws ReflectiveOperationException {
        var instruction = InstructionDecoder.decode("3A20");

        assertInstanceOf(Xorlw.class, instruction);
    }

    @Test
    public void decode_addlw() throws ReflectiveOperationException {
        var instruction = InstructionDecoder.decode("3E25");

        assertInstanceOf(Addlw.class, instruction);
    }

    @Test
    public void decode_goto() throws ReflectiveOperationException {
        var instruction = InstructionDecoder.decode("2806");

        assertInstanceOf(Goto.class, instruction);
    }
}
