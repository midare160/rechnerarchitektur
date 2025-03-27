package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.Common.Tuple;

import java.io.*;
import java.util.List;

public class LstParser {
    public static List<Instruction> parseFile(String path) throws IOException {
        try (var reader = new BufferedReader(new FileReader(path))) {
            return reader.lines()
                    .map(l -> Tuple.of(l.split("\\s+", 3), l)) // Split on ANY whitespace
                    .map(li -> new Instruction(
                            Integer.parseInt(li.item1()[0], 16),
                            Integer.parseInt(li.item1()[1], 16),
                            Integer.parseInt(li.item1()[2]),
                            li.item1()[3],
                            li.item2()
                    ))
                    .toList();
        }
    }
}
