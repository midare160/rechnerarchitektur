package com.lhmd.rechnerarchitektur.parsing;

import com.lhmd.rechnerarchitektur.instructions.*;

import java.io.*;
import java.util.*;

public class LstParser {
    public static List<InstructionViewModel> parseFile(String path) throws IOException {
        try (var reader = new BufferedReader(new FileReader(path))) {
            return reader.lines()
                    .map(LstParser::getInstruction)
                    .toList();
        }
    }

    private static InstructionViewModel getInstruction(String line) {
        var address = line.substring(0, 4);
        var instruction = line.substring(5, 9);
        var lineNumber = line.substring(20, 25);
        var comment = line.substring(25);

        return new InstructionViewModel(
                address,
                instruction,
                lineNumber,
                comment,
                line);
    }
}
