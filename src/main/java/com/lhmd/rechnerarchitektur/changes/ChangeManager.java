package com.lhmd.rechnerarchitektur.changes;

/**
 * Manages (temporary) values to perform (internal) changes.
 */
public class ChangeManager {
    private boolean isChanging;

    public TempValue<Boolean> beginChange() {
        return new TempValue<>(isChanging, true, c -> isChanging = c);
    }

    public boolean isChanging() {
        return isChanging;
    }
}
