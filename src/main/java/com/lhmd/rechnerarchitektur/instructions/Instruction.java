package com.lhmd.rechnerarchitektur.instructions;

import javafx.beans.property.*;

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

    private final BooleanProperty isBreakpointActive;
    private final ObjectProperty<URL> breakpointSvgUrl;

    public Instruction(UUID id, String programCounter, String instruction, String lineNumber, String comment, String rawText) {
        this.id = id;
        this.programCounter = programCounter;
        this.instruction = instruction;
        this.lineNumber = lineNumber;
        this.comment = comment;
        this.rawText = rawText;

        isBreakpointActive = new SimpleBooleanProperty(false);
        breakpointSvgUrl = new SimpleObjectProperty<>();
    }

    public UUID getId() {
        return id;
    }

    public String getRawText() {
        return rawText;
    }

    public BooleanProperty isBreakpointActiveProperty() {
        return isBreakpointActive;
    }

    public boolean isBreakpointActive() {
        return isBreakpointActive.get();
    }

    public void setIsBreakpointActive(boolean active) {
        isBreakpointActive.set(active);
    }

    // Do NOT rename the following methods, they're expected by the FXML

    public ObjectProperty<URL> breakpointSvgUrlProperty() {
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
