package com.lhmd.rechnerarchitektur.instructions;

import javafx.beans.property.*;

import java.net.URL;

@SuppressWarnings({"unused"})
public class InstructionViewModel {
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

        isBreakpointActive = new SimpleBooleanProperty(false);
        breakpointSvgUrl = new SimpleObjectProperty<>();
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

    public String getAddress() {
        return address;
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
