package com.lhmd.rechnerarchitektur.instructions;

import javafx.beans.property.*;

import java.net.URL;

@SuppressWarnings({"unused"})
public class InstructionViewModel implements RawInstruction {
    private final String address;
    private final String instruction;
    private final String lineNumber;
    private final String comment;
    private final String rawText;

    private final BooleanProperty isBreakpointActive;
    private final ObjectProperty<URL> breakpointSvgUrl;

    public InstructionViewModel(String address, String instruction, String lineNumber, String comment, String rawText) {
        this.address = address;
        this.instruction = instruction;
        this.lineNumber = lineNumber;
        this.comment = comment;
        this.rawText = rawText;

        this.isBreakpointActive = new SimpleBooleanProperty(false);
        this.breakpointSvgUrl = new SimpleObjectProperty<>();
    }

    // Do NOT rename the following methods, they're expected by the FXML

    public BooleanProperty isBreakpointActiveProperty() {
        return isBreakpointActive;
    }

    public ObjectProperty<URL> breakpointSvgUrlProperty() {
        return breakpointSvgUrl;
    }

    public String getRawText() {
        return rawText;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
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
