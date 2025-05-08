package com.lhmd.rechnerarchitektur.parsing;

import com.lhmd.rechnerarchitektur.common.*;
import com.lhmd.rechnerarchitektur.instructions.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class InstructionParser {
    public static final Charset CHARSET = StandardCharsets.ISO_8859_1;

    public static <T extends Instruction> List<T> parseFile(Class<T> instructionClass, String path) throws IOException {
        try (var reader = Files.newBufferedReader(Path.of(path), CHARSET)) {
            return reader.lines()
                    .map(l -> parseLine(instructionClass, l))
                    .toList();
        }
    }

    public static <T extends Instruction> T parseLine(Class<T> instructionClass, String line) {
        var instruction = Runner.unchecked(() -> instructionClass.getDeclaredConstructor().newInstance());

        instruction.setAddress(IntUtils.tryParse(line.substring(0, 4), 16));
        instruction.setInstruction(IntUtils.tryParse(line.substring(5, 9), 16));
        instruction.setLineNumber(Integer.parseInt(line.substring(20, 25)));
        instruction.setComment(line.substring(25));

        return instruction;
    }
}
