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
    public void decode_swapf() {
        var instruction = InstructionDecoder.decode(0x0E8D);

        assertInstanceOf(Swapf.class, instruction);
    }

    @Test
    public void decode_incf() {
        var instruction = InstructionDecoder.decode(0x0A8D);

        assertInstanceOf(Incf.class, instruction);
    }

    @Test
    public void decode_comf() {
        var instruction = InstructionDecoder.decode(0x090D);

        assertInstanceOf(Comf.class, instruction);
    }

    @Test
    public void decode_movf() {
        var instruction = InstructionDecoder.decode(0x088C);

        assertInstanceOf(Movf.class, instruction);
    }

    @Test
    public void decode_addwf() {
        var instruction = InstructionDecoder.decode(0x070C);

        assertInstanceOf(Addwf.class, instruction);
    }

    @Test
    public void decode_xorwf() {
        var instruction = InstructionDecoder.decode(0x068C);

        assertInstanceOf(Xorwf.class, instruction);
    }

    @Test
    public void decode_iorwf() {
        var instruction = InstructionDecoder.decode(0x048C);

        assertInstanceOf(Iorwf.class, instruction);
    }

    @Test
    public void decode_andfw() {
        var instruction = InstructionDecoder.decode(0x050C);

        assertInstanceOf(Andwf.class, instruction);
    }

    @Test
    public void decode_decf() {
        var instruction = InstructionDecoder.decode(0x030C);

        assertInstanceOf(Decf.class, instruction);
    }

    @Test
    public void decode_subwf() {
        var instruction = InstructionDecoder.decode(0x020D);

        assertInstanceOf(Subwf.class, instruction);
    }

    @Test
    public void decode_clrf() {
        var instruction = InstructionDecoder.decode(0x018C);

        assertInstanceOf(Clrf.class, instruction);
    }

    @Test
    public void decode_clrw() {
        var instruction = InstructionDecoder.decode(0x0100);

        assertInstanceOf(Clrw.class, instruction);
    }

    @Test
    public void decode_movfw() {
        var instruction = InstructionDecoder.decode(0x008C);

        assertInstanceOf(Movwf.class, instruction);
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
