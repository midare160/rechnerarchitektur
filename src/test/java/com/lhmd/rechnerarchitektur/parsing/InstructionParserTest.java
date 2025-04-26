package com.lhmd.rechnerarchitektur.parsing;

import com.lhmd.rechnerarchitektur.instructions.Instruction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class InstructionParserTest {
    @Test
    public void parseLine_MixedLines_parsesCorrectly() throws IOException {
        final var rawLines = """
                                    00016           org 0
                                    00017  start
                0000 3011           00018           movlw 11h           ;in W steht nun 11h, Statusreg. unverändert
                0001 3930           00019           andlw 30h           ;W = 10h, C=x, DC=x, Z=0
                """;

        var tempFilePath = Files.createTempFile(null, ".lst");
        tempFilePath.toFile().deleteOnExit();
        Files.writeString(tempFilePath, rawLines);

        var instructions = InstructionParser.parseFile(TestInstruction.class, tempFilePath.toString());

        assertEquals(4, instructions.size());

        var first = instructions.getFirst();
        assertNull(first.getAddress());
        assertNull(first.getInstruction());
        assertEquals(16, first.getLineNumber());
        assertEquals("org 0", first.getComment().trim());

        var second = instructions.get(1);
        assertNull(second.getAddress());
        assertNull(second.getInstruction());
        assertEquals(17, second.getLineNumber());
        assertEquals("start", second.getComment().trim());

        var third = instructions.get(2);
        assertEquals(0x0000, third.getAddress());
        assertEquals(0x3011, third.getInstruction());
        assertEquals(18, third.getLineNumber());
        assertEquals("movlw 11h           ;in W steht nun 11h, Statusreg. unverändert", third.getComment().trim());

        var fourth = instructions.get(3);
        assertEquals(0x0001, fourth.getAddress());
        assertEquals(0x3930, fourth.getInstruction());
        assertEquals(19, fourth.getLineNumber());
        assertEquals("andlw 30h           ;W = 10h, C=x, DC=x, Z=0", fourth.getComment().trim());
    }

    public static class TestInstruction implements Instruction {
        private Integer address;
        private Integer instruction;
        private int lineNumber;
        private String comment;

        @Override
        public Integer getAddress() {
            return address;
        }

        @Override
        public void setAddress(Integer address) {
            this.address = address;
        }

        @Override
        public Integer getInstruction() {
            return instruction;
        }

        @Override
        public void setInstruction(Integer instruction) {
            this.instruction = instruction;
        }

        @Override
        public int getLineNumber() {
            return lineNumber;
        }

        @Override
        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        @Override
        public String getComment() {
            return comment;
        }

        @Override
        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}
