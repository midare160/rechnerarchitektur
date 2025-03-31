package com.lhmd.rechnerarchitektur.Instructions;

import javafx.beans.property.SimpleObjectProperty;

import java.net.URL;
import java.util.UUID;

@SuppressWarnings({"unused"})
public class Instruction {
    private final UUID id;
    private final String programCounter;
    private final String instruction;
    private final String lineNumber;
    private final String comment;
    private final String rawText;

    private final SimpleObjectProperty<URL> breakpointSvgUrl;

    public Instruction(UUID id, String programCounter, String instruction, String lineNumber, String comment, String rawText) {
        this.id = id;
        this.programCounter = programCounter;
        this.instruction = instruction;
        this.lineNumber = lineNumber;
        this.comment = comment;
        this.rawText = rawText;

        breakpointSvgUrl = new SimpleObjectProperty<>();
    }

    public UUID getId() {
        return id;
    }

    public String getRawText() {
        return rawText;
    }

    // Do NOT rename the following methods, they're expected by the FXML

    public SimpleObjectProperty<URL> breakpointSvgUrlProperty() {
        return breakpointSvgUrl;
    }

    public URL getBreakpointSvgUrl() {
        return breakpointSvgUrl.get();
    }

    public void setBreakpointSvgUrl(URL url) {
        breakpointSvgUrl.set(url);
    }

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
}
