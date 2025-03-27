package com.lhmd.rechnerarchitektur;

import java.io.*;
import java.util.List;

public class LstParser {
    public static List<Instruction> parseFile(String path) throws IOException {
        try (var reader = new BufferedReader(new FileReader(path))) {
            return reader.lines()
                    .map(LstParser::getInstruction)
                    .toList();
        }
    }

    private static Instruction getInstruction(String line) {
        var programCounter = line.substring(0, 4);
        var instruction = line.substring(5, 9);
        var lineNumber = line.substring(20, 25);
        var comment = line.substring(25);

        return new Instruction(
                programCounter,
                instruction,
                lineNumber,
                comment.trim(),
                line);
    }
}
