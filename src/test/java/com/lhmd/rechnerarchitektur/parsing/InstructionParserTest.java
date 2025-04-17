package com.lhmd.rechnerarchitektur.parsing;

import com.lhmd.rechnerarchitektur.instructions.RawInstruction;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InstructionParserTest {
    @Test
    public void parseRawInstructions_validInstructions_returnsParsedValues() {
        var rawInstructions = List.of(
                new TestInstruction("0002", "380D"),
                new TestInstruction("0003", "3C3D")
        );

        var parsedInstructions = InstructionParser.parseRawInstructions(rawInstructions);
        assertEquals(2, parsedInstructions.size());

        var firstInstruction = parsedInstructions.get(0x0002);
        assertEquals(0x380D, firstInstruction.getInstruction());

        var secondInstruction = parsedInstructions.get(0x0003);
        assertEquals(0x3C3D, secondInstruction.getInstruction());
    }

    @Test
    public void parseRawInstructions_partlyInvalidInstructions_returnsOnlyValidParsedValues() {
        var rawInstructions = List.of(
                new TestInstruction(null, "380D"),
                new TestInstruction("0002", "380D"),
                new TestInstruction(null, null),
                new TestInstruction("0003", "3C3D"),
                new TestInstruction("0004", null)
        );

        var parsedInstructions = InstructionParser.parseRawInstructions(rawInstructions);
        assertEquals(2, parsedInstructions.size());
    }

    private record TestInstruction(String address, String instruction) implements RawInstruction {
        @Override
        public String getAddress() {
            return address;
        }

        @Override
        public String getInstruction() {
            return instruction;
        }
    }
}
