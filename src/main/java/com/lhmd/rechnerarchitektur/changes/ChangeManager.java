package com.lhmd.rechnerarchitektur.changes;

/**
 * Manages (temporary) values to perform (internal) changes.
 */
public class ChangeManager {
    private boolean isChanging;
    private boolean isChanged;

    public TempValue<Boolean> beginChange() {
        return new TempValue<>(isChanging, true, c -> isChanging = c);
    }

    public boolean isChanging() {
        return isChanging;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }
}
