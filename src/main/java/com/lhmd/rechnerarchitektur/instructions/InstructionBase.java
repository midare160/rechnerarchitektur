package com.lhmd.rechnerarchitektur.instructions;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;

public abstract class InstructionBase {
    protected static final String SCOPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    private boolean isInitialized;
    private int instruction;

    public final int getInstruction() {
        return instruction;
    }

    public final void setInstruction(int instruction) {
        if (this.isInitialized) {
            throw new IllegalStateException("Instruction was already initialized");
        }

        this.instruction = instruction;
        onInitialized();
        this.isInitialized = true;
    }

    public abstract void execute();

    public boolean isTwoCycle() {
        return false;
    }

    protected void onInitialized() {
    }
}
