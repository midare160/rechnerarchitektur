package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.common.IntUtils;

import java.io.*;
import java.util.*;

public class LstParser {
    public static List<Instruction> parseFile(String path) throws IOException {
        try (var reader = new BufferedReader(new FileReader(path))) {
            return reader.lines()
                    .map(LstParser::getInstruction)
                    .toList();
        }
    }

    public static List<ParsedInstruction> parseInstructions(List<Instruction> instructions) {
        return instructions.stream()
                .map(LstParser::getParsedInstruction)
                .filter(Objects::nonNull)
                .toList();
    }

    private static Instruction getInstruction(String line) {
        var programCounter = line.substring(0, 4);
        var instruction = line.substring(5, 9);
        var lineNumber = line.substring(20, 25);
        var comment = line.substring(25).trim();

        return new Instruction(
                UUID.randomUUID(),
                programCounter,
                instruction,
                lineNumber,
                comment,
                line);
    }

    private static ParsedInstruction getParsedInstruction(Instruction i) {
        var parsedProgramCounter = IntUtils.tryParse(i.getProgramCounter(), 16);
        var parsedInstruction = IntUtils.tryParse(i.getInstruction(), 16);

        if (parsedProgramCounter == null || parsedInstruction == null) {
            return null;
        }

        return new ParsedInstruction(i.getId(), parsedProgramCounter, parsedInstruction, i.getRawText());
    }
}
