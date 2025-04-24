package com.lhmd.rechnerarchitektur.instructions;

import javafx.beans.property.*;

import java.net.URL;

@SuppressWarnings({"unused"})
public class InstructionRowModel implements Instruction {
    private final BooleanProperty isBreakpointActive;
    private final ObjectProperty<URL> breakpointSvgUrl;
    private final BooleanProperty isNext;

    private final ObjectProperty<Integer> address;
    private final ObjectProperty<Integer> instruction;
    private final IntegerProperty lineNumber;
    private final StringProperty comment;

    public InstructionRowModel() {
        this.isBreakpointActive = new SimpleBooleanProperty();
        this.breakpointSvgUrl = new SimpleObjectProperty<>();
        this.isNext = new SimpleBooleanProperty();

        this.address = new SimpleObjectProperty<>();
        this.instruction = new SimpleObjectProperty<>();
        this.lineNumber = new SimpleIntegerProperty();
        this.comment = new SimpleStringProperty();
    }

    @Override
    public Integer getAddress() {
        return address.get();
    }

    @Override
    public void setAddress(Integer address) {
        this.address.set(address);
    }

    @Override
    public Integer getInstruction() {
        return instruction.get();
    }

    @Override
    public void setInstruction(Integer instruction) {
        this.instruction.set(instruction);
    }

    @Override
    public int getLineNumber() {
        return lineNumber.get();
    }

    @Override
    public void setLineNumber(int lineNumber) {
        this.lineNumber.set(lineNumber);
    }

    @Override
    public String getComment() {
        return comment.get();
    }

    @Override
    public void setComment(String comment) {
        this.comment.set(comment);
    }

    // Do NOT rename the following methods, they're expected by the FXML

    public BooleanProperty isBreakpointActiveProperty() {
        return isBreakpointActive;
    }

    public ObjectProperty<URL> breakpointSvgUrlProperty() {
        return breakpointSvgUrl;
    }

    public BooleanProperty isNextProperty() {
        return isNext;
    }

    public ObjectProperty<Integer> addressProperty() {
        return address;
    }

    public ObjectProperty<Integer> instructionProperty() {
        return instruction;
    }

    public IntegerProperty lineNumberProperty() {
        return lineNumber;
    }

    public StringProperty commentProperty() {
        return comment;
    }
}
