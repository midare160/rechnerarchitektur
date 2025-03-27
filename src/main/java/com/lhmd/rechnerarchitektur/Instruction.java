package com.lhmd.rechnerarchitektur;

public record Instruction(int programCounter, int instruction, int lineNumber, String comment, String rawText) {
}
