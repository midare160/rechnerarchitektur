package com.lhmd.rechnerarchitektur;

@SuppressWarnings({"ClassCanBeRecord", "unused"})
public final class Instruction {
    private final String programCounter;
    private final String instruction;
    private final String lineNumber;
    private final String comment;
    private final String rawText;

    public Instruction(String programCounter, String instruction, String lineNumber, String comment, String rawText) {
        this.programCounter = programCounter;
        this.instruction = instruction;
        this.lineNumber = lineNumber;
        this.comment = comment;
        this.rawText = rawText;
    }

    // Do NOT rename getter methods, they're expected from the FXML

    public String getProgramCounter() {
        return programCounter;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getComment() {
        return comment;
    }

    public String getRawText() {
        return rawText;
    }
}
