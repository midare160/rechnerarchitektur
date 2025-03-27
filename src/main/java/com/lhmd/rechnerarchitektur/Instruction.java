package com.lhmd.rechnerarchitektur;

public final class Instruction {
    private final Integer programCounter;
    private final Integer instruction;
    private final int lineNumber;
    private final String comment;
    private final String rawText;

    public Instruction(Integer programCounter, Integer instruction, int lineNumber, String comment, String rawText) {
        this.programCounter = programCounter;
        this.instruction = instruction;
        this.lineNumber = lineNumber;
        this.comment = comment;
        this.rawText = rawText;
    }

    public Integer getProgramCounter() {
        return programCounter;
    }

    public Integer getInstruction() {
        return instruction;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getComment() {
        return comment;
    }

    public String getRawText() {
        return rawText;
    }
}
